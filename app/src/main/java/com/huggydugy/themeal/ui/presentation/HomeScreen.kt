package com.huggydugy.themeal.ui.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.huggydugy.themeal.R
import com.huggydugy.themeal.data.model.Category
import com.huggydugy.themeal.data.model.Meal
import com.huggydugy.themeal.data.model.event.MealEvent
import com.huggydugy.themeal.ui.theme.Black
import com.huggydugy.themeal.ui.theme.Gray
import com.huggydugy.themeal.ui.theme.Gray2
import com.huggydugy.themeal.ui.theme.Gray3
import com.huggydugy.themeal.ui.theme.Red
import com.huggydugy.themeal.ui.theme.Red2
import com.huggydugy.themeal.ui.theme.White
import com.huggydugy.themeal.ui.theme.White2

val cards = listOf(
    R.drawable.image1,
    R.drawable.image2
)


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController()
){
    val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
    val stateCategory by homeViewModel.stateCategory.collectAsState()
    val selectedCategory by homeViewModel.selectedCategory.collectAsState()
    val selectedMeals by homeViewModel.selectedMeals.collectAsState()
    val isBottomVisible by homeViewModel.isBottomSheetVisible.collectAsState()

    val lazyListState = rememberLazyListState()
    //val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val modalBottomSheetState = rememberModalBottomSheetState()
    Scaffold(
        topBar = {
           TopAppBar()
        },
        bottomBar = {
            BottomBar()
        }
    ) {padding ->
        Column(
            modifier = Modifier
                .background(White)
                .padding(padding)
        ) {
            if (selectedMeals.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center),
                    color = Red
                )
            }
            // Так называемые баннеры
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(cards.size){
                    EventCard(image = cards[it])
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            // Строка категорий
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(horizontal = 10.dp)
            ){
                items(stateCategory){ category->
                    ButtonCatalog(
                        label = category.strCategory ?: "",
                        isSelected = selectedCategory == category,
                        onButtonClicked = {
                            homeViewModel.onCategorySelected(category)
                        }
                    )
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            // И столбец с различными блюдами
            LazyColumn(
                state = lazyListState
            ) {
                items(selectedMeals){meal: Meal ->
                    MealCard(meal = meal){

                        homeViewModel.onEvent(MealEvent.ShowDialog)
                    }
                }

            }
            if (isBottomVisible){
                ModalBottomSheet(
                    onDismissRequest = { homeViewModel.onEvent(MealEvent.HideDialog) },
                    sheetState = modalBottomSheetState,
                ) {

                }
            }
        }
    }
}


@Composable
fun MealCard(meal: Meal , onMealCardClicked: () -> Unit) {
    val imagerPainter = rememberAsyncImagePainter(model = meal.strMealThumb)
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(16.dp)
            .height(150.dp)
            .clickable { onMealCardClicked() },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = imagerPainter,
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.FillBounds,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = meal.strMeal,
                    color = Black,
                    fontSize = 18.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                ButtonPrice {

                }

            }
        }
    }
}

@Composable
fun ButtonPrice(
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        border = BorderStroke(1.dp, Red),
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .wrapContentSize(),
        colors = ButtonDefaults.buttonColors(
            contentColor = Red,
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(15)
    ) {
        Text(
            text = "Check",
            fontSize = 16.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(
    image: Int
){
    Card(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .width(300.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(15))
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "null"
        )

    }
}


@Composable
private fun ButtonCatalog(
    label: String,
    onButtonClicked: () -> Unit,
    isSelected: Boolean
){
    Box(
        modifier = Modifier
            .shadow(
                elevation = 8.dp,
                ambientColor = Black,
                shape = RoundedCornerShape(15)
            )
            .wrapContentSize()
            .clip(RoundedCornerShape(15))
            .clickable { onButtonClicked() }
            .background(if (isSelected) Red2 else White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) Red else Gray,
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 10.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(){
    TopAppBar(
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.qrcode),
                    contentDescription = "qrcode",
                    modifier = Modifier.size(25.dp)
                )
            }
        },
        title = {},
        navigationIcon = {
            Row(
                modifier = Modifier.padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Moscow",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "ArrowDown"
                    )
                }

            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White
        )
    )
}

@Composable
private fun BottomBar() {
    val selectedIndex = remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = White2,
        contentColor = Gray
    ) {
        NavigationBarItem(
            label = {
                Text(text = "Menu", fontWeight = FontWeight.Bold)
            },
            selected = selectedIndex.intValue == 0,
            onClick = {
                selectedIndex.intValue = 0
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon1),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor= Red,
                selectedTextColor = Red,
                unselectedIconColor = Gray2,
                unselectedTextColor = Gray2,
                indicatorColor =  White2
            )
        )
        NavigationBarItem(
            label = {
                Text(text = "Account", fontWeight = FontWeight.Bold)
            },
            selected = selectedIndex.intValue == 2,
            onClick = {
                selectedIndex.intValue = 2
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon3),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor= Red,
                selectedTextColor = Red,
                unselectedIconColor = Gray2,
                unselectedTextColor = Gray2,
                indicatorColor =  White2
            )
        )
        NavigationBarItem(
            label = {
                Text(text = "Basket", fontWeight = FontWeight.Bold)
            },
            selected = selectedIndex.intValue == 1,
            onClick = {
                selectedIndex.intValue = 1
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon2),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor= Red,
                selectedTextColor = Red,
                unselectedIconColor = Gray2,
                unselectedTextColor = Gray2,
                indicatorColor =  White2
            )
        )

    }
}

