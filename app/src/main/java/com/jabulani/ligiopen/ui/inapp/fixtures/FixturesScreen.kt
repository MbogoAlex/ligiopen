package com.jabulani.ligiopen.ui.inapp.fixtures

import android.os.Build
import android.widget.CalendarView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.jabulani.ligiopen.AppViewModelFactory
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.data.network.model.club.ClubDetails
import com.jabulani.ligiopen.data.network.model.match.fixture.FixtureData
import com.jabulani.ligiopen.data.network.model.match.fixture.MatchStatus
import com.jabulani.ligiopen.data.network.model.match.fixture.fixtures
import com.jabulani.ligiopen.ui.inapp.clubs.LoadingStatus
import com.jabulani.ligiopen.ui.inapp.home.HomeScreenTab
import com.jabulani.ligiopen.ui.nav.AppNavigation
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.ui.utils.formatIsoDateTime
import com.jabulani.ligiopen.ui.utils.formatLocalDate
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

object FixturesScreenDestination: AppNavigation {
    override val title: String = "Fixtures screen"
    override val route: String = "fixtures-screen"
    val clubId: String = "clubId"
    val routeWithClubId: String = "$route/{$clubId}"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FixturesScreenComposable(
    clubId: Int? = null,
    navigateToPostMatchScreen: (postMatchId: String, fixtureId: String, locationId: String) -> Unit,
    navigateToLoginScreenWithArgs: (email: String, password: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: FixturesViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if(clubId != null) {
            viewModel.updateClubId(
                clubId = clubId
            )
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        when(lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                viewModel.getInitialData()
            }
        }
    }

    if(uiState.loadingStatus == LoadingStatus.FAIL) {
        if(uiState.unauthorized) {
            val email = uiState.userAccount.email
            val password = uiState.userAccount.password

            navigateToLoginScreenWithArgs(email, password)
        }
        viewModel.resetStatus()
    }

    Box(
        modifier = modifier
            .safeDrawingPadding()
    ) {
        FixturesScreen(
            selectedDate = uiState.selectedDate,
            onSelectDate = viewModel::selectDate,
            clubs = uiState.clubs,
            selectedClubs = uiState.selectedClubs,
            onSelectClub = viewModel::selectClubs,
            onDeselectClub = viewModel::deselectClub,
            onResetClubs = viewModel::resetClubs,
            matchDateTimes = uiState.matchDateTimes,
            fixtures = uiState.fixtures,
            loadingStatus = uiState.loadingStatus,
            navigateToPostMatchScreen = navigateToPostMatchScreen,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FixturesScreen(
    selectedDate: LocalDate?,
    clubs: List<ClubDetails>,
    selectedClubs: List<ClubDetails>,
    onSelectDate: (date: LocalDate?) -> Unit,
    onSelectClub: (clubDetails: ClubDetails) -> Unit,
    onDeselectClub: (clubDetails: ClubDetails) -> Unit,
    onResetClubs: () -> Unit,
    fixtures: List<FixtureData>,
    matchDateTimes: List<String>,
    loadingStatus: LoadingStatus,
    navigateToPostMatchScreen: (postMatchId: String, fixtureId: String, locationId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val allowedDates: List<LocalDate> = rememberSaveable(matchDateTimes) {
        matchDateTimes
            .mapNotNull { runCatching {              // (a) safest way—ignore bad input
                LocalDateTime.parse(it)              // ISO-8601: “2025-05-10T14:00:00”
                    .toLocalDate()
            }.getOrNull() }
            
    }



    var showFilter by rememberSaveable {
        mutableStateOf(false)
    }
    
    var showClubsPicker by rememberSaveable {
        mutableStateOf(false)
    }


    var showDatePicker by rememberSaveable { mutableStateOf(false) }



    if (showDatePicker) {
        AlertDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = { showDatePicker = false }) {
                    Text(
                        "Done",
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                }
            },
            dismissButton = {
                Row {
                    TextButton(
                        onClick = { showDatePicker = false },
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Dismiss",
                            fontSize = screenFontSize(x = 14.0).sp
                        )
                    }
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    onClick = {
                        onSelectDate(null)
                        showDatePicker = false
                    },
                    modifier = Modifier
                ) {
                    Text(
                        text = "Reset",
                        color = MaterialTheme.colorScheme.onError,
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Select match date",
                        fontSize = screenFontSize(x = 16.0).sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
                    LazyColumn {
                        items(allowedDates) {date ->
                            if(selectedDate == date) {
                                Button(
                                    onClick = { /*TODO*/ },
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                ) {
                                    Text(
                                        text = formatLocalDate(date),
                                        fontSize = screenFontSize(x = 14.0).sp
                                    )
                                }
                            } else {
                                TextButton(
                                    onClick = { onSelectDate(date) },
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                ) {
                                    Text(
                                        text = formatLocalDate(date),
                                        fontSize = screenFontSize(x = 14.0).sp
                                    )
                                }
                            }


                        }
                    }
                }
            }
        )
    }
    
    if(showClubsPicker) {
        AlertDialog(
            onDismissRequest = { showClubsPicker = false },
            confirmButton = {
                Button(onClick = { showClubsPicker = false }) {
                    Text(
                        "Done",
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                }
            },
            dismissButton = {
                Row {
                    TextButton(
                        onClick = { showClubsPicker = false },
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Dismiss",
                            fontSize = screenFontSize(x = 14.0).sp
                        )
                    }
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    onClick = {
                        onResetClubs()
                        showClubsPicker = false
                    },
                    modifier = Modifier
                ) {
                    Text(
                        text = "Reset",
                        color = MaterialTheme.colorScheme.onError,
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Select clubs",
                        fontSize = screenFontSize(x = 16.0).sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
                    LazyColumn {
                        items(clubs) {club ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = club.name,
                                    fontSize = screenFontSize(x = 14.0).sp
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                if(selectedClubs.contains(club)) {
                                    IconButton(onClick = { onDeselectClub(club) }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.checked_box),
                                            contentDescription = "Deselect"
                                        )
                                    }
                                } else {
                                    IconButton(onClick = { onSelectClub(club) }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.unchecked_box),
                                            contentDescription = "Select"
                                        )
                                    }
                                }


                            }

                        }
                    }
                }
            }
        )
    }


    Column {
        if(showFilter) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = screenWidth(x = 8.0),
                        vertical = screenHeight(x = 4.0)
                    )
            ) {
                OutlinedButton(onClick = { showDatePicker = !showDatePicker }) {
                    Text(
                        text = selectedDate?.let { formatLocalDate(it) } ?: "Date: All",
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                }
                Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
                OutlinedButton(onClick = { showClubsPicker = !showClubsPicker }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Club",
                            fontSize = screenFontSize(x = 14.0).sp
                        )
                        Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                        Text(text = "|")
                        Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                        Text(
                            text = if(selectedClubs.isEmpty()) "All" else selectedClubs.size.toString(),
                            fontSize = screenFontSize(x = 14.0).sp
                        )
                    }
                    
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { showFilter = false }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close filtering"
                    )
                }
            }
            
        } else {
            Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = screenWidth(x = 8.0)
                    )

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ligiopen_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(screenWidth(x = 56.0))
                )
                Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
                Text(
                    text = HomeScreenTab.MATCHES.name.lowercase().replaceFirstChar { first -> first.uppercase() },
                    fontSize = screenFontSize(x = 26.0).sp,
                    fontWeight = FontWeight.W900
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    enabled = true,
                    onClick = {showFilter = true}) {
                    Icon(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "Filter"
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = screenHeight(x = 16.0),
                    horizontal = screenWidth(x = 16.0)
                )
        ) {
            if(loadingStatus == LoadingStatus.LOADING) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            } else {
                if(fixtures.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "No fixtures found",
                            fontSize = screenFontSize(x = 14.0).sp
                        )
                    }
                } else {
                    LazyColumn {
                        items(fixtures) { fixture ->
                            FixtureCard(
                                fixtureData = fixture,
                                navigateToPostMatchScreen = navigateToPostMatchScreen
                            )
                            Spacer(modifier = Modifier.height(screenHeight(x = 24.0)))
                        }
                    }
                }

            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(Modifier.fillMaxWidth()) {
        daysOfWeek.forEach { dow ->
            Text(
                text = dow.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}




@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FixtureCard(
    fixtureData: FixtureData,
    navigateToPostMatchScreen: (postMatchId: String, fixtureId: String, locationId: String) -> Unit,
    modifier: Modifier = Modifier
) {

    ElevatedCard(
        onClick = {
            navigateToPostMatchScreen(
                fixtureData.postMatchAnalysisId.toString(),
                fixtureData.matchFixtureId.toString(),
                fixtureData.matchLocationId.toString()
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    screenWidth(x = 16.0)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(screenWidth(x = 4.0)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val matchStatusColor = when (fixtureData.matchStatus) {
                    MatchStatus.PENDING -> Color(0xFF9E9E9E) // Gray - Neutral for pending matches
                    MatchStatus.COMPLETED -> Color(0xFF4CAF50) // Green - Indicates match completion
                    MatchStatus.CANCELLED -> Color(0xFFF44336) // Red - Signifies cancellation
                    MatchStatus.FIRST_HALF -> Color(0xFFFFC107) // Amber - Active but early stage
                    MatchStatus.HALF_TIME -> Color(0xFFFF9800) // Orange - Signals break time
                    MatchStatus.SECOND_HALF -> Color(0xFFFFC107) // Amber - Ongoing second half
                    MatchStatus.EXTRA_TIME_FIRST_HALF -> Color(0xFFFF5722) // Deep Orange - More intensity
                    MatchStatus.EXTRA_TIME_HALF_TIME -> Color(0xFFFF9800) // Orange - Half-time in extra time
                    MatchStatus.EXTRA_TIME_SECOND_HALF -> Color(0xFFFF5722) // Deep Orange - More intensity
                    MatchStatus.PENALTY_SHOOTOUT -> Color(0xFF673AB7) // Purple - Indicates a high-stakes moment
                }


                val matchStatusIcon = when (fixtureData.matchStatus) {
                    MatchStatus.PENDING -> R.drawable.clock // Clock icon
                    MatchStatus.COMPLETED -> R.drawable.check_mark // Checkmark icon
                    MatchStatus.CANCELLED -> R.drawable.close // Cross icon
                    MatchStatus.FIRST_HALF -> R.drawable.ball
                    MatchStatus.HALF_TIME -> R.drawable.half_time
                    MatchStatus.SECOND_HALF -> R.drawable.ball
                    MatchStatus.EXTRA_TIME_FIRST_HALF -> R.drawable.ball
                    MatchStatus.EXTRA_TIME_HALF_TIME -> R.drawable.half_time
                    MatchStatus.EXTRA_TIME_SECOND_HALF -> R.drawable.ball
                    MatchStatus.PENALTY_SHOOTOUT -> R.drawable.ball
                }

                Icon(
                    painter = painterResource(id = matchStatusIcon),
                    contentDescription = fixtureData.matchStatus.name,
                    tint = matchStatusColor,
                    modifier = Modifier.size(screenWidth(x = 16.0))
                )

                Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                Text(
                    text = fixtureData.matchStatus.name,
                    color = matchStatusColor,
                    fontSize = screenFontSize(x = 14.0).sp,
                    fontWeight = FontWeight.Bold
                )

//                Spacer(modifier = Modifier.weight(1f))


//                TextButton(
//                    onClick = {
//                        navigateToPostMatchScreen(fixtureData.postMatchAnalysisId.toString(), fixtureData.matchFixtureId.toString(), fixtureData.matchLocationId.toString())
//                    }
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = "Match details",
//                            fontSize = screenFontSize(x = 14.0).sp
//                        )
//                        Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
//                            contentDescription = "Match details"
//                        )
//                    }
//                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Row {
                        AsyncImage(
                            model = fixtureData.homeClub.clubLogo.link,
                            contentDescription = fixtureData.homeClub.name,
                            modifier = Modifier
                                .size(screenWidth(x = 24.0))
                                .clip(RoundedCornerShape(screenWidth(x = 8.0))),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                        Column {
                            Text(
                                text = fixtureData.homeClub.name,
                                fontSize = screenFontSize(x = 16.0).sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
                            Text(
                                text = "HOME",
                                fontSize = screenFontSize(x = 10.0).sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if(fixtureData.matchStatus != MatchStatus.COMPLETED) {
                        Text(
                            text = "VS",
                            fontSize = screenFontSize(x = 16.0).sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if(fixtureData.matchStatus != MatchStatus.CANCELLED && fixtureData.matchStatus != MatchStatus.PENDING) {
                        Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = fixtureData.homeClubScore!!.toString(),
                                fontSize = screenFontSize(x = 16.0).sp,
                                fontWeight = FontWeight.W900
                            )
                            Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                            Text(
                                text = ":",
                                fontSize = screenFontSize(x = 16.0).sp,
                                fontWeight = FontWeight.W900
                            )
                            Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                            Text(
                                text = fixtureData.awayClubScore!!.toString(),
                                fontSize = screenFontSize(x = 16.0).sp,
                                fontWeight = FontWeight.W900
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = fixtureData.awayClub.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = screenFontSize(x = 16.0).sp
                            )
                            Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
                            Text(
                                text = "AWAY",
                                fontSize = screenFontSize(x = 10.0).sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                        AsyncImage(
                            model = fixtureData.awayClub.clubLogo.link,
                            contentDescription = fixtureData.awayClub.name,
                            modifier = Modifier
                                .size(screenWidth(x = 24.0))
                                .clip(RoundedCornerShape(screenWidth(x = 8.0))),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))


            Text(
                text = formatIsoDateTime(LocalDateTime.parse(fixtureData.matchDateTime)),
                fontSize = screenFontSize(x = 12.0).sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            // Show "Buy Tickets" button if match is PENDING
//            if (fixtureData.matchStatus == MatchStatus.PENDING) {
//                Button(
//                    onClick = { /* Handle ticket purchase */ },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFF2962FF), // Blue button
//                        contentColor = Color.White
//                    )
//                ) {
//                    Text(text = "Buy Tickets", fontSize = screenFontSize(x = 14.0).sp)
//                    Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
//                    Icon(
//                        painter = painterResource(id = R.drawable.ticket),
//                        contentDescription = "Buy Tickets"
//                    )
//                }
//                Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
//            }
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FixturesScreenPreview() {
    LigiopenTheme {
        FixturesScreen(
            selectedClubs = emptyList(),
            onSelectClub = {},
            onResetClubs = {},
            onSelectDate = {},
            selectedDate = null,
            clubs = emptyList(),
            onDeselectClub = {},
            matchDateTimes = emptyList(),
            fixtures = fixtures,
            loadingStatus = LoadingStatus.SUCCESS,
            navigateToPostMatchScreen = {postMatchId, fixtureId, locationId ->  }
        )
    }
}