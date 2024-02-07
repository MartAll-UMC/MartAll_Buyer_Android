package com.org.martall.model

import com.org.martall.R

data class ItemDTO(
    val imageUrl: Int = 0,
    val name: String = "",
    val price: Int = 0,
    val store: String = "",
    val category: Int = 0,
    var isLiked: Boolean = false,
)

var dummyItems: List<ItemDTO> = listOf(
    ItemDTO(R.drawable.img_item_banana_360dp, "바나나", 5000, "오렌지마트 광운대점", 0, false),
    ItemDTO(R.drawable.img_item_banana01_360dp, "바나나", 5000, "하이마트 광운대점", 0, false),
    ItemDTO(R.drawable.img_item_banana02_360dp, "바나나", 5000, "맘마농가식자재마트", 0, false),
    ItemDTO(R.drawable.img_item_banana03_360dp, "바나나", 5000, "숲속마트", 0, false),
    ItemDTO(R.drawable.img_item_tangerine01_360dp, "귤", 5000, "오렌지마트 광운대점", 0, false),
    ItemDTO(R.drawable.img_item_tangerine02_360dp, "귤", 5000, "하이마트 광운대점", 0, false),
    ItemDTO(R.drawable.img_item_tangerine03_360dp, "귤", 5000, "맘마농가식자재마트", 0, false),
    ItemDTO(R.drawable.img_item_meat01_360dp, "돼지고기 1근", 10200, "오렌지마트 광운대점", 1, false),
    ItemDTO(R.drawable.img_item_meat02_360dp, "돼지고기 1근", 12200, "오렌지마트 광운대점", 1, false),
    ItemDTO(R.drawable.img_item_meat03_360dp, "돼지고기 1근", 14400, "오렌지마트 광운대점", 1, false),
    ItemDTO(R.drawable.img_item_meat04_360dp, "소고기", 20200, "오렌지마트 광운대점", 1, false),
    ItemDTO(R.drawable.img_item_meat05_360dp, "구이용 소고기 모둠", 28900, "오렌지마트 광운대점", 1, false),
    ItemDTO(R.drawable.img_item_detergent01_360dp, "세탁 세제 베리향", 8000, "오렌지마트 광운대점", 4, false),
    ItemDTO(R.drawable.img_item_detergent02_360dp, "세탁 세제 코튼향", 8200, "오렌지마트 광운대점", 4, false),
    ItemDTO(R.drawable.img_item_detergent03_360dp, "세탁 세제 스위트핑크", 7900, "오렌지마트 광운대점", 4, false),
)

var dummyFruitItems: List<ItemDTO> = dummyItems.filter { it.category == 0 }
var dummyMeatItems: List<ItemDTO> = dummyItems.filter { it.category == 1 }
var dummyFishItems: List<ItemDTO> = dummyItems.filter { it.category == 2 }
var dummySnackItems: List<ItemDTO> = dummyItems.filter { it.category == 3 }
var dummyDailyItems: List<ItemDTO> = dummyItems.filter { it.category == 4 }

var dummyKeywordItems: List<String> = listOf(
    "바나나", "귤", "삼겹살", "항정살", "세탁세제", "커피", "펩시콜라", "초콜릿", "과자", "신라면"
)