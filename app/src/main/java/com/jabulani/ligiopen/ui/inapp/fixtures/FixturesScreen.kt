package com.jabulani.ligiopen.ui.inapp.fixtures

import android.os.Build
import android.widget.CalendarView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
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
import com.jabulani.ligiopen.ui.theme.backgroundLight
import com.jabulani.ligiopen.ui.theme.errorLight
import com.jabulani.ligiopen.ui.theme.onErrorLight
import com.jabulani.ligiopen.ui.theme.onPrimaryLight
import com.jabulani.ligiopen.ui.theme.onSurfaceLight
import com.jabulani.ligiopen.ui.theme.onSurfaceVariantLight
import com.jabulani.ligiopen.ui.theme.onTertiaryLight
import com.jabulani.ligiopen.ui.theme.primaryLight
import com.jabulani.ligiopen.ui.theme.surfaceContainerHighLight
import com.jabulani.ligiopen.ui.theme.surfaceLight
import com.jabulani.ligiopen.ui.theme.surfaceVariantLight
import com.jabulani.ligiopen.ui.theme.tertiaryLight
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
    showTopBanner: Boolean = true,
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
            showTopBanner = showTopBanner,
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

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FixturesScreen(
    showTopBanner: Boolean,
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
    // Theme colors
    val primaryColor = primaryLight
    val surfaceColor = surfaceContainerHighLight
    val onSurfaceColor = onSurfaceLight
    val accentColor = tertiaryLight
    val errorColor = errorLight

    val allowedDates = rememberSaveable(matchDateTimes) {
        matchDateTimes.mapNotNull {
            runCatching { LocalDateTime.parse(it).toLocalDate() }.getOrNull()
        }
    }

    var showFilter by rememberSaveable { mutableStateOf(false) }
    var showClubsPicker by rememberSaveable { mutableStateOf(false) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }


    // Game-style date picker
    if (showDatePicker) {
        AlertDialog(
            onDismissRequest = { showDatePicker = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = surfaceColor
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "SELECT MATCH DATE",
                        style = MaterialTheme.typography.headlineMedium,
                        color = primaryColor,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyColumn(modifier = Modifier.weight(1f, false)) {
                        items(allowedDates) { date ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                elevation = if (selectedDate == date) CardDefaults.cardElevation(8.dp)
                                else CardDefaults.cardElevation(2.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (selectedDate == date) primaryColor.copy(alpha = 0.2f)
                                    else surfaceVariantLight
                                ),
                            ) {
                                TextButton(
                                    onClick = {
                                        onSelectDate(date)
                                        showDatePicker = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = formatLocalDate(date),
                                        color = if (selectedDate == date) primaryColor
                                        else onSurfaceVariantLight,
                                        fontWeight = if (selectedDate == date) FontWeight.Bold
                                        else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = {
                                onSelectDate(null)
                                showDatePicker = false
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = errorColor.copy(alpha = 0.1f),
                                contentColor = errorColor
                            ),
                            border = BorderStroke(1.dp, errorColor)
                        ) {
                            Text("RESET")
                        }

                        Button(
                            onClick = { showDatePicker = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor,
                                contentColor = onPrimaryLight
                            )
                        ) {
                            Text("DONE")
                        }
                    }
                }
            }
        }
    }

    // Game-style club picker
    if (showClubsPicker) {
        AlertDialog(
            onDismissRequest = { showClubsPicker = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = surfaceColor
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "SELECT CLUBS",
                        style = MaterialTheme.typography.headlineMedium,
                        color = primaryColor,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyColumn(modifier = Modifier.weight(1f, false)) {
                        items(clubs) { club ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                elevation = if (selectedClubs.contains(club)) CardDefaults.cardElevation(8.dp)
                                else CardDefaults.cardElevation(2.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (selectedClubs.contains(club)) primaryColor.copy(alpha = 0.2f)
                                    else surfaceVariantLight
                                ),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = club.clubLogo.link,
                                        contentDescription = club.name,
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = club.name,
                                        color = if (selectedClubs.contains(club)) primaryColor
                                        else onSurfaceVariantLight,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Icon(
                                        painter = painterResource(
                                            if (selectedClubs.contains(club)) R.drawable.checked_box
                                            else R.drawable.unchecked_box
                                        ),
                                        contentDescription = null,
                                        tint = if (selectedClubs.contains(club)) primaryColor
                                        else onSurfaceVariantLight,
                                        modifier = Modifier
                                            .clickable {
                                                if (selectedClubs.contains(club)) {
                                                    onDeselectClub(club)
                                                } else {
                                                    onSelectClub(club)
                                                }
                                            }
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = {
                                onResetClubs()
                                showClubsPicker = false
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = errorColor.copy(alpha = 0.1f),
                                contentColor = errorColor
                            ),
                            border = BorderStroke(1.dp, errorColor)
                        ) {
                            Text("RESET")
                        }

                        Button(
                            onClick = { showClubsPicker = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor,
                                contentColor = onPrimaryLight
                            )
                        ) {
                            Text("DONE")
                        }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with game-style gradient
        if (showTopBanner) {
            if (showFilter) {
                // Filter controls with game aesthetics
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = surfaceColor
                    ),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        // Date filter button
                        OutlinedButton(
                            onClick = { showDatePicker = true },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = surfaceVariantLight,
                                contentColor = primaryColor
                            ),
                            border = BorderStroke(1.dp, primaryColor),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.calendar),
                                    contentDescription = "Date",
                                    tint = primaryColor
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = selectedDate?.let { formatLocalDate(it) } ?: "All Dates",
                                    color = primaryColor
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Club filter button
                        OutlinedButton(
                            onClick = { showClubsPicker = true },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = surfaceVariantLight,
                                contentColor = primaryColor
                            ),
                            border = BorderStroke(1.dp, primaryColor),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.team),
                                    contentDescription = "Clubs",
                                    tint = primaryColor
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (selectedClubs.isEmpty()) "All Clubs"
                                    else "${selectedClubs.size} Selected",
                                    color = primaryColor
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Close button
                        IconButton(
                            onClick = { showFilter = false },
                            modifier = Modifier
                                .size(40.dp)
                                .background(primaryColor, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = onPrimaryLight
                            )
                        }
                    }
                }
            } else {
                // Main header with game logo and title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    primaryColor.copy(alpha = 0.8f),
                                    primaryColor.copy(alpha = 0.4f)
                                )
                            )
                        )
                        .padding(horizontal = 8.dp, vertical = 12.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ligiopen_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(2.dp, accentColor, RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = HomeScreenTab.MATCHES.name
                            .lowercase()
                            .replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.headlineMedium,
                        color = onPrimaryLight,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        modifier = Modifier
                            .shadow(4.dp, shape = RoundedCornerShape(4.dp))
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Filter button with badge if filters are active
                    Box {
                        IconButton(
                            onClick = { showFilter = true },
                            modifier = Modifier
                                .size(40.dp)
                                .background(accentColor, CircleShape)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.filter),
                                contentDescription = "Filter",
                                tint = onTertiaryLight
                            )
                        }

                        // Show badge if any filters are active
                        if (selectedDate != null || selectedClubs.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(16.dp)
                                    .background(errorColor, CircleShape)
                                    .border(2.dp, surfaceColor, CircleShape)
                            ) {
                                Text(
                                    text = "!",
                                    color = onErrorLight,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Main content area
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            when (loadingStatus) {
                LoadingStatus.LOADING -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp),
                            strokeWidth = 4.dp,
                            color = accentColor
                        )
                    }
                }
                else -> {
                    if (fixtures.isEmpty()) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.cancel_event),
                                    contentDescription = "No matches",
                                    tint = primaryColor.copy(alpha = 0.5f),
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "NO MATCHES FOUND",
                                    color = primaryColor.copy(alpha = 0.7f),
                                    fontWeight = FontWeight.Bold
                                )
                                if (selectedDate != null || selectedClubs.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    TextButton(
                                        onClick = {
                                            onSelectDate(null)
                                            onResetClubs()
                                        }
                                    ) {
                                        Text("RESET FILTERS")
                                    }
                                }
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            items(fixtures) { fixture ->
                                FixtureCard(
                                    fixtureData = fixture,
                                    navigateToPostMatchScreen = navigateToPostMatchScreen,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
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
    val primaryColor = primaryLight
    val accentColor = tertiaryLight
    val backgroundColor = backgroundLight
    val surfaceColor = surfaceLight
    val onSurfaceColor = onSurfaceLight
    val surfaceVariantColor = surfaceVariantLight
    val matchStatusColor = when (fixtureData.matchStatus) {
        MatchStatus.PENDING -> Color(0xFF9E9E9E)
        MatchStatus.COMPLETED -> Color(0xFF4CAF50)
        MatchStatus.CANCELLED -> errorLight
        MatchStatus.FIRST_HALF -> Color(0xFFFFC107)
        MatchStatus.HALF_TIME -> Color(0xFFFF9800)
        MatchStatus.SECOND_HALF -> Color(0xFFFFC107)
        MatchStatus.EXTRA_TIME_FIRST_HALF -> Color(0xFFFF5722)
        MatchStatus.EXTRA_TIME_HALF_TIME -> Color(0xFFFF9800)
        MatchStatus.EXTRA_TIME_SECOND_HALF -> Color(0xFFFF5722)
        MatchStatus.PENALTY_SHOOTOUT -> Color(0xFF673AB7)
    }

    val matchStatusIcon = when (fixtureData.matchStatus) {
        MatchStatus.PENDING -> R.drawable.clock
        MatchStatus.COMPLETED -> R.drawable.check_mark
        MatchStatus.CANCELLED -> R.drawable.close
        MatchStatus.FIRST_HALF -> R.drawable.ball
        MatchStatus.HALF_TIME -> R.drawable.half_time
        MatchStatus.SECOND_HALF -> R.drawable.ball
        MatchStatus.EXTRA_TIME_FIRST_HALF -> R.drawable.ball
        MatchStatus.EXTRA_TIME_HALF_TIME -> R.drawable.half_time
        MatchStatus.EXTRA_TIME_SECOND_HALF -> R.drawable.ball
        MatchStatus.PENALTY_SHOOTOUT -> R.drawable.ball
    }

    Card(
        onClick = {
            navigateToPostMatchScreen(
                fixtureData.postMatchAnalysisId.toString(),
                fixtureData.matchFixtureId.toString(),
                fixtureData.matchLocationId.toString()
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = surfaceContainerHighLight
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Match status bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = matchStatusColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = matchStatusIcon),
                    contentDescription = fixtureData.matchStatus.name,
                    tint = matchStatusColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = fixtureData.matchStatus.name
                        .lowercase()
                        .replace("_", " ")
                        .replaceFirstChar { it.uppercase() },
                    color = matchStatusColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Teams and score
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Home team
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    AsyncImage(
                        model = fixtureData.homeClub.clubLogo.link,
                        contentDescription = fixtureData.homeClub.name,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .border(2.dp, primaryColor, CircleShape)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = fixtureData.homeClub.name,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "HOME",
                        color = primaryColor.copy(alpha = 0.7f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Score or VS
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(0.8f)
                ) {
                    if (fixtureData.matchStatus == MatchStatus.COMPLETED ||
                        fixtureData.matchStatus == MatchStatus.FIRST_HALF ||
                        fixtureData.matchStatus == MatchStatus.SECOND_HALF ||
                        fixtureData.matchStatus == MatchStatus.EXTRA_TIME_FIRST_HALF ||
                        fixtureData.matchStatus == MatchStatus.EXTRA_TIME_SECOND_HALF ||
                        fixtureData.matchStatus == MatchStatus.PENALTY_SHOOTOUT) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = fixtureData.homeClubScore?.toString() ?: "0",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = " : ",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = fixtureData.awayClubScore?.toString() ?: "0",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Black
                            )
                        }
                    } else {
                        Text(
                            text = "VS",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Black,
                            color = primaryColor
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Match time
                    Text(
                        text = formatIsoDateTime(LocalDateTime.parse(fixtureData.matchDateTime)),
                        color = onSurfaceVariantLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Away team
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    AsyncImage(
                        model = fixtureData.awayClub.clubLogo.link,
                        contentDescription = fixtureData.awayClub.name,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .border(2.dp, primaryColor, CircleShape)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = fixtureData.awayClub.name,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "AWAY",
                        color = primaryColor.copy(alpha = 0.7f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Glowing accent at bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                primaryColor.copy(alpha = 0.3f),
                                primaryColor,
                                primaryColor.copy(alpha = 0.3f)
                            )
                        )
                    )
            )
        }
    }
}




@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FixturesScreenPreview() {
    LigiopenTheme {
        FixturesScreen(
            showTopBanner = true,
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