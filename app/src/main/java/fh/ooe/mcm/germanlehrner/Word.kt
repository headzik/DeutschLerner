package fh.ooe.mcm.germanlehrner

import android.os.Parcel
import android.os.Parcelable

data class Word(var englishWord: String,
                var article: String,
                var germanWord: String,
                var plural: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(englishWord)
        dest?.writeString(article)
        dest?.writeString(germanWord)
        dest?.writeString(plural)
    }

    override fun describeContents(): Int {
        return this.hashCode()
    }

    companion object CREATOR : Parcelable.Creator<Word> {
        override fun createFromParcel(parcel: Parcel): Word {
            return Word(parcel)
        }

        override fun newArray(size: Int): Array<Word?> {
            return arrayOfNulls(size)
        }
    }
}