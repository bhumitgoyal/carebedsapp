package com.example.carebedsapp



import android.os.Parcel
import android.os.Parcelable

data class Bed(
    val id: Int,
    val hospitalName: String,
    val location: String,
    var status: String // "Available" or "Occupied"
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(hospitalName)
        parcel.writeString(location)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Bed> {
        override fun createFromParcel(parcel: Parcel): Bed {
            return Bed(parcel)
        }

        override fun newArray(size: Int): Array<Bed?> {
            return arrayOfNulls(size)
        }
    }
}
