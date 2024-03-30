package com.huggydugy.themeal.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.huggydugy.themeal.R
import com.huggydugy.themeal.ui.theme.Black
import com.huggydugy.themeal.ui.theme.Gray
import com.huggydugy.themeal.ui.theme.Gray2
import com.huggydugy.themeal.ui.theme.Red
import com.huggydugy.themeal.ui.theme.White

val cards = listOf(
    R.drawable.image1,
    R.drawable.image2
)

@Preview(showBackground = true)
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController()
){
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
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(cards.size){
                    CardImage(image = cards[it])
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
        }

    }


}


@Composable
fun CardImage(
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
            .wrapContentSize()
            .clickable { onButtonClicked() }
            .clip(RoundedCornerShape(30))
            .background(if (isSelected) Red else Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.White else Color.Gray,
            modifier = Modifier.padding(18.dp)
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
                    text = "Москва",
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
        }
    )
}

@Composable
private fun BottomBar() {
    val selectedIndex = remember { mutableIntStateOf(0) }

    NavigationBar(
        modifier = Modifier.shadow(10.dp, ambientColor = Black),
        containerColor = if (isSystemInDarkTheme()) Red else White,
        contentColor = Gray
    ) {
        NavigationBarItem(
            label = {
                Text(text = "Меню", fontWeight = FontWeight.Bold)
            },
            selected = selectedIndex.intValue == 0,
            onClick = {
                selectedIndex.intValue = 0
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon1),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor= Red,
                selectedTextColor = Red,
                unselectedIconColor = Gray2,
                unselectedTextColor = Gray2,
                indicatorColor = if (isSystemInDarkTheme()) Red else White
            )
        )
        NavigationBarItem(
            label = {
                Text(text = "Профиль", fontWeight = FontWeight.Bold)
            },
            selected = selectedIndex.intValue == 2,
            onClick = {
                selectedIndex.intValue = 2
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon3),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor= Red,
                selectedTextColor = Red,
                unselectedIconColor = Gray2,
                unselectedTextColor = Gray2,
                indicatorColor = if (isSystemInDarkTheme()) Red else White
            )
        )
        NavigationBarItem(
            label = {
                Text(text = "Корзина", fontWeight = FontWeight.Bold)
            },
            selected = selectedIndex.intValue == 1,
            onClick = {
                selectedIndex.intValue = 1
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon2),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor= Red,
                selectedTextColor = Red,
                unselectedIconColor = Gray2,
                unselectedTextColor = Gray2,
                indicatorColor = if (isSystemInDarkTheme()) Red else White
            )
        )

    }
}

