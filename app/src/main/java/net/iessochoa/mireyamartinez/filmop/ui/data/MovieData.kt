package net.iessochoa.mireyamartinez.filmop.data

import android.os.Parcel
import android.os.Parcelable

data class MovieData(
    val movieId: String = "",
    val name: String = "",
    val duration: String = "",
    val genre: String = "",
    var rating: Double = 0.0,
    val platforms: List<String> = emptyList(),
    val image: String = "",
    val synopsis: String = "",
    var opinion: String = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.createStringArrayList()!!,
        parcel.readString()!!,
        parcel.readString()!!
        )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(movieId)
        parcel.writeString(name)
        parcel.writeString(duration)
        parcel.writeString(genre)
        parcel.writeDouble(rating)
        parcel.writeStringList(platforms)
        parcel.writeString(synopsis)
        parcel.writeString(opinion)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<MovieData> {
        override fun createFromParcel(parcel: Parcel): MovieData {
            return MovieData(parcel)
        }

        override fun newArray(size: Int): Array<MovieData?> {
            return arrayOfNulls(size)
        }
    }
}
