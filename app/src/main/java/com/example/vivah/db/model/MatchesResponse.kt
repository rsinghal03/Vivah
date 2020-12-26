package com.example.vivah.db.model


data class MatchesResponse(
    val info: Info,
    val results: List<Result>
) {
    data class Info(
        val page: Int, // 1
        val results: Int, // 10
        val seed: String, // 925feeeec0480b38
        val version: String // 1.3
    )

    data class Result(
        val cell: String, // 637-892-4839
        val dob: Dob,
        val email: String, // delphine.smith@example.com
        val gender: String, // female
        val id: Id,
        val location: Location,
        val login: Login,
        val name: Name,
        val nat: String, // CA
        val phone: String, // 148-181-5876
        val picture: Picture,
        val registered: Registered
    ) {
        data class Dob(
            val age: Int, // 39
            val date: String // 1981-07-07T12:28:03.173Z
        )

        data class Id(
            val name: String,
            val value: Any? // null
        )

        data class Location(
            val city: String, // Lasalle
            val coordinates: Coordinates,
            val country: String, // Canada
            val postcode: Any?, // null
            val state: String, // Prince Edward Island
            val street: Street,
            val timezone: Timezone
        ) {
            data class Coordinates(
                val latitude: String, // -64.4501
                val longitude: String // 129.6649
            )

            data class Street(
                val name: String, // St. Catherine St
                val number: Int // 2531
            )

            data class Timezone(
                val description: String, // Midway Island, Samoa
                val offset: String // -11:00
            )
        }

        data class Login(
            val md5: String, // 5558f11468fef59c07b7d7052142c202
            val password: String, // 789456
            val salt: String, // oVGxsV3r
            val sha1: String, // 753321d8792fd61ee4aa9d9479184187a796be14
            val sha256: String, // 9ac1cbe1f02afc51681dae2058f22fec62ca7939ba3a1358fb3e31f6aa2c0bbd
            val username: String, // heavybird675
            val uuid: String // 5d504d8b-63c8-454c-9407-0aa5f27d1e29
        )

        data class Name(
            val first: String, // Delphine
            val last: String, // Smith
            val title: String // Miss
        )

        data class Picture(
            val large: String, // https://randomuser.me/api/portraits/women/92.jpg
            val medium: String, // https://randomuser.me/api/portraits/med/women/92.jpg
            val thumbnail: String // https://randomuser.me/api/portraits/thumb/women/92.jpg
        )

        data class Registered(
            val age: Int, // 12
            val date: String // 2008-03-06T18:38:02.087Z
        )
    }
}