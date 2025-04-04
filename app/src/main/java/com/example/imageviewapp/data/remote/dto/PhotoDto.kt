package com.example.imageviewapp.data.remote.dto


data class PhotoDto(
    val id: String,
    val urls: UrlsDto,
    val user: UserDto,
    val likes: Int,
    val description: String?
)

data class UrlsDto(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class UserDto(
    val id: String,
    val username: String,
    val name: String,
    val profile_image: ProfileImageDto
)

data class ProfileImageDto(
    val small: String,
    val medium: String,
    val large: String
)

data class SearchResultDto(
    val total: Int,
    val total_pages: Int,
    val results: List<PhotoDto>
)

data class PhotoDetailDto(
    val id: String,
    val created_at: String,
    val updated_at: String,
    val width: Int,
    val height: Int,
    val color: String,
    val likes: Int,
    val description: String?,
    val urls: UrlsDto,
    val user: UserDto,
    val exif: ExifDto?,
    val location: LocationDto?
)

data class ExifDto(
    val make: String?,
    val model: String?,
    val exposure_time: String?,
    val aperture: String?,
    val focal_length: String?,
    val iso: Int?
)

data class LocationDto(
    val city: String?,
    val country: String?,
    val position: PositionDto?
)

data class PositionDto(
    val latitude: Double?,
    val longitude: Double?
)
