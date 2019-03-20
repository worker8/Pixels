package com.worker8.redditapi

object Constants {

    val subreddits = listOf(
        "pics",
        "aww",
        "art",
        "gifs",
        "SweatyPalms",
        "OldSchoolCool",
        "QuotesPorn",
        "HistoryPorn",
        "funny",
        "PixelArt",
        "drawing",
        "wallpaper+wallpapers",
        "RedditGetsDrawn",
        "ImaginaryMonsters",
        "MicroPorn",
        "MacroPorn",
        "CrappyDesign",
        "ExposurePorn",
        "ITookAPicture",
        "IDAP",
        "illustration",
        "doodles",
        "earthporn",
        "mildlyInteresting",
        "awwnverts",
        "cats",
        "CatPictures",
        "DogPictures",
        "husky+corgi",
        "AnimalPorn",
        "DelightfullyChubby",
        "HardcoreAww",
        "food",
        "FoodPorn",
        "DessertPorn",
        "EarthPorn",
        "JapanPics",
        "WinterPorn",
        "SpacePorn",
        "WaterPorn",
        "ImaginaryLandscapes",
        "SpaceFlightPorn",
        "CarPorn",
        "GunPorn",
        "RoomPorn",
        "CityPorn",
        "VillagePorn",
        "ArchitecturePorn",
        "InfrastructurePorn"
    )

    fun getRandomSubreddit(): String {
        val randomPosition = (0 until subreddits.count()).shuffled().first()
        return subreddits[randomPosition]
    }
}
