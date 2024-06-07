package com.tripod.durust.presentation.home.individuals

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripod.durust.R
import com.tripod.durust.presentation.home.ImageFrame


enum class DoctorProfession(val professionName: String) {
    GENERAL_PRACTITIONER("General Practitioner"),
    PEDIATRICIAN("Pediatrician"),
    DERMATOLOGIST("Dermatologist"),
    CARDIOLOGIST("Cardiologist"),
    NEUROLOGIST("Neurologist"),
    ORTHOPEDIC_SURGEON("Orthopedic Surgeon"),
    PSYCHIATRIST("Psychiatrist"),
    RADIOLOGIST("Radiologist"),
    ANESTHESIOLOGIST("Anesthesiologist"),
    ONCOLOGIST("Oncologist"),
    ENDOCRINOLOGIST("Endocrinologist"),
    GASTROENTEROLOGIST("Gastroenterologist"),
    PULMONOLOGIST("Pulmonologist"),
    RHEUMATOLOGIST("Rheumatologist"),
    UROLOGIST("Urologist"),
    NEPHROLOGIST("Nephrologist"),
    OPHTHALMOLOGIST("Ophthalmologist"),
    OTOLARYNGOLOGIST("Otolaryngologist (ENT)"),
    OBSTETRICIAN_GYNECOLOGIST("Obstetrician/Gynecologist"),
    PLASTIC_SURGEON("Plastic Surgeon"),
    PATHOLOGIST("Pathologist"),
    HEMATOLOGIST("Hematologist"),
    IMMUNOLOGIST("Immunologist"),
    INFECTIOUS_DISEASE_SPECIALIST("Infectious Disease Specialist"),
    SPORTS_MEDICINE_SPECIALIST("Sports Medicine Specialist"),
    EMERGENCY_MEDICINE_SPECIALIST("Emergency Medicine Specialist"),
    FAMILY_MEDICINE_DOCTOR("Family Medicine Doctor"),
    GERIATRICIAN("Geriatrician"),
    PHYSICIAN("Physician"),
    NUTRITIONIST("Nutritionist"),
}


@Composable
fun DoctorSearch() {

    var address by remember { mutableStateOf("") }
    var selectedProfession by remember { mutableStateOf<DoctorProfession?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp,end = 16.dp, top = 16.dp, bottom = 100.dp)
    ) {
        DoctorRecommendations()
        Text(text = "Doctors near you", fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier  = Modifier.height(8.dp))
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Enter address") },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = selectedProfession?.professionName ?: "Doctor’s specialty",
            onValueChange = {},
            label = { Text("Choose:") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier.clickable { showDialog = true }
                )
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialog = true }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (address.isNotEmpty() && selectedProfession != null) {
                    val query = "${selectedProfession?.professionName} near $address"
                    val uri = Uri.parse("https://www.google.com/search?q=${Uri.encode(query)}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                }
                else{
                    Toast.makeText(context, "Please fill in the address and choose a profession", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }
        Spacer(modifier = Modifier.height(16.dp))
        ImageFrame(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            imgRes = R.drawable.mappng,
            width = 300,
            height = 200
        ) {
            if (address.isNotEmpty() && selectedProfession != null) {
                val query = "${selectedProfession?.professionName} near $address"
                val uri =
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode(query)}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }
            else{
                Toast.makeText(context, "Please fill in the address and choose a profession", Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (showDialog) {
        BasicAlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Choose Doctor's Specialty") },
            text = {
                val scrollState = rememberScrollState()
                Box(modifier = Modifier.height(400.dp)) {
                    Column(
                        modifier = Modifier.verticalScroll(scrollState)
                    ) {
                        DoctorProfession.entries.forEach { profession ->
                            DropdownMenuItem(
                                text = {
                                    Text(profession.professionName)
                                },
                                onClick = {
                                    selectedProfession = profession
                                    showDialog = false
                                })
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }
}

@Composable
fun BasicAlertDialog(
    onDismissRequest: () -> Unit,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    confirmButton: @Composable (() -> Unit)? = null,
    dismissButton: @Composable (() -> Unit)? = null,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = title,
        text = text,
        confirmButton = confirmButton ?: {},
        dismissButton = dismissButton ?: {},
        shape = RoundedCornerShape(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DoctorSearchPreview() {
    DoctorSearch()
}