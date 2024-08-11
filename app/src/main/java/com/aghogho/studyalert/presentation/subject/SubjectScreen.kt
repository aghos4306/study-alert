package com.aghogho.studyalert.presentation.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.aghogho.studyalert.domain.model.Subject
import com.aghogho.studyalert.dummySession
import com.aghogho.studyalert.dummyTask
import com.aghogho.studyalert.presentation.components.AddSubjectDialog
import com.aghogho.studyalert.presentation.components.CountCard
import com.aghogho.studyalert.presentation.components.DeleteDialog
import com.aghogho.studyalert.presentation.components.studySessionsList
import com.aghogho.studyalert.presentation.components.taskList
import com.aghogho.studyalert.presentation.destinations.SubjectScreenRouteDestination
import com.aghogho.studyalert.presentation.destinations.TaskScreenRouteDestination
import com.aghogho.studyalert.presentation.task.TaskScreenNavArgs
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

data class SubjectScreenNavArgs(
    val subjectId: Int
)

@Destination(navArgsDelegate = SubjectScreenNavArgs::class)
@Composable
fun SubjectScreenRoute(
    navigator: DestinationsNavigator
) {
    SubjectScreen(
        onBackButtonClick = { navigator.navigateUp() },
        onAddTaskButtonClick = {
            val navArgToTaskScreen = TaskScreenNavArgs(taskId = null, subjectId = -1)
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArgToTaskScreen))
        },
        onTaskCardClick = { taskId ->
            val navArgToTaskScreen = TaskScreenNavArgs(taskId = taskId, subjectId = null)
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArgToTaskScreen))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectScreen(
    onBackButtonClick: () -> Unit,
    onAddTaskButtonClick: () -> Unit,
    onTaskCardClick: (Int?) -> Unit
) {
    val listState = rememberLazyListState()
    val isFABExpanded by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    val scrollBehaviorType = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    var isEditSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteDialogSessionOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteDialogSubjectOpen by rememberSaveable { mutableStateOf(false) }

    var subjectName by remember { mutableStateOf("") }
    var goalHours by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Subject.subjectCardColors.random()) }

    AddSubjectDialog(
        isOpen = isEditSubjectDialogOpen,
        onDismissRequest = { isEditSubjectDialogOpen = false },
        onConfirmButtonClick = {
            isEditSubjectDialogOpen = false
        },
        goalHours = goalHours,
        subjectName = subjectName,
        onGoalHoursChanged = { goalHours = it },
        onSubjectNameChange = { subjectName = it },
        onColorChange = { selectedColor = it },
        selectedColors = selectedColor
    )

    // Delete Session
    DeleteDialog(
        isOpen = isDeleteDialogSessionOpen,
        title = "Delete Session?",
        bodyText = "Are you sure, you want to delete this session? Your studied hours will be reduced " +
                "by this session time. This action can not be undone.",
        onDismissRequest = { isDeleteDialogSessionOpen = false },
        onConfirmButtonClick = { isDeleteDialogSessionOpen = false }
    )

    // Delete Subject
    DeleteDialog(
        isOpen = isDeleteDialogSubjectOpen,
        title = "Delete Subject?",
        bodyText = "Are you sure you want to delete this subject? All related " +
            "tasks and study sessions will be permanently removed. This action cannot be undone",
        onDismissRequest = { isDeleteDialogSubjectOpen = false },
        onConfirmButtonClick = { isDeleteDialogSubjectOpen = false }
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehaviorType.nestedScrollConnection),
        topBar = {
            SubjectScreenTopBar(
                title = "Kotlin Advanced",
                onBackButtonClick = onBackButtonClick,
                onDeleteButtonClick = { isDeleteDialogSessionOpen = true },
                onEditButtonClick = { isEditSubjectDialogOpen = true },
                scrollBehavior = scrollBehaviorType
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddTaskButtonClick,
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Add") },
                text = { Text(text = "Add Task") },
                expanded = isFABExpanded
            )
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                SubjectOverviewSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    studiedHours = "10",
                    goalHours = "15",
                    progress = 0.55f
                )
            }
            // TaskList for upcoming tasks
            taskList(
                sectionTitle = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks. \n " +
                        "Click the + button in subject screen to add new task.",
                //tasks = emptyList()
                tasks = dummyTask,
                onCheckBoxClick = {},
                onTaskCardClick = onTaskCardClick
            )

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            // TaskList for completed tasks
            taskList(
                sectionTitle = "COMPLETED TASKS",
                emptyListText = "You don't have any completed tasks. \n " +
                        "Click the check box on completion of task.",
                //tasks = emptyList()
                tasks = dummyTask,
                onCheckBoxClick = {},
                onTaskCardClick = onTaskCardClick
            )

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            // StudySessionList
            studySessionsList(
                sectionTitle = "RECENT STUDY SESSION",
                emptyListText = "You don't have any upcoming sessions. \n " +
                        "Start a session to begin recording your progress.",
                //sessions = emptyList(),
                sessions = dummySession,
                onDeleteIconClick = { isDeleteDialogSessionOpen = true }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectScreenTopBar(
    title: String,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigation Back Icon"
                )
            }
        },
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            IconButton(onClick = onDeleteButtonClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Subject"
                )
            }
            IconButton(onClick = onEditButtonClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Subject"
                )
            }
        }
    )
}

@Composable
private fun SubjectOverviewSection(
    modifier: Modifier,
    studiedHours: String,
    goalHours: String,
    progress: Float
) {
    // use remember to calculate the progress only when the progress value change.
    val percentageProgress = remember(progress) {
        (progress * 100).toInt().coerceIn(0, 100)
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            count = goalHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Study Hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier.size(75.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = 1f,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progress,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
            )
            Text(text = "$percentageProgress")
        }
    }
}
