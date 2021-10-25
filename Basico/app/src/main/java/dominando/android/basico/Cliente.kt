package dominando.android.basico

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel
class Cliente(var codigo: Int = 0, var nome: String = ""){
//    @ParcelConstructor constructor(): this(0, "")

    /*
    * CÓDIGO ACIMA, uso do Parceler de forma simplificada
    *
    * */


//    constructor(parcel: Parcel) : this (
//        parcel.readInt(),
//        parcel.readString()) {}
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(codigo)
//        parcel.writeString(nome)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Cliente> {
//        override fun createFromParcel(parcel: Parcel): Cliente {
//            return Cliente(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Cliente?> {
//            return arrayOfNulls(size)
//        }
//    }
}