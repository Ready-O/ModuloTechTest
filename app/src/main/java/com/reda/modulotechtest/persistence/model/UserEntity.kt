package com.reda.modulotechtest.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reda.modulotechtest.model.Address
import com.reda.modulotechtest.model.User

@Entity(
    tableName = "user"
)
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "birth_date")
    val birthDate: String,
    val city: String,
    @ColumnInfo(name = "postal_code")
    val postalCode: String,
    val street: String,
    @ColumnInfo(name = "street_code")
    val streetCode: String,
    val country: String
)

fun UserEntity.toUser(): User = User(
    firstName = this.firstName,
    lastName = this.lastName,
    birthDate = this.birthDate,
    address = Address(
        city = this.city,
        postalCode = this.postalCode,
        street = this.street,
        streetCode = this.streetCode,
        country = this.country
    )
)

fun User.toUserEntity(): UserEntity = UserEntity(
    firstName = this.firstName,
    lastName = this.lastName,
    birthDate = this.birthDate,
    city = this.address.city,
    postalCode = this.address.postalCode,
    street = this.address.street,
    streetCode = this.address.streetCode,
    country = this.address.country
)
