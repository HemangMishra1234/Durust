package com.tripod.durust.data

import com.tripod.durust.presentation.datacollection.TimeEntity
import com.tripod.durust.presentation.datacollection.getTime
import com.tripod.durust.presentation.home.individuals.DoctorProfession

data class AppointmentEntity(
    val id: String = getTime().toString(),
    val date: DateEntity = DateEntity(),
    val time: TimeEntity = TimeEntity(),
    val doctorName: String = "",
    val doctorSpeciality: DoctorProfession = DoctorProfession.GENERAL_PRACTITIONER,
)

data class AppointmentDataUpload(
    val list: List<AppointmentEntity>
)
