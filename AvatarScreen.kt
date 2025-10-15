package com.example.avatar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ComponentTransform(
    var offsetX: Float = 0f,
    var offsetY: Float = 0f,
    var scale: Float = 1f
)

@Composable
fun AvatarScreen() {
    var selectedComponent by remember { mutableStateOf("Mata") }
    var isPanelOpen by remember { mutableStateOf(true) }

    val transforms = remember {
        mutableStateMapOf(
            "Wajah" to ComponentTransform(),
            "Mata" to ComponentTransform(),
            "Alis" to ComponentTransform(),
            "Hidung" to ComponentTransform(),
            "Mulut" to ComponentTransform()
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Area Preview Avatar (Kiri)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                AvatarCompose(transforms)
            }

            // Panel Kontrol (Kanan) - Conditional
            if (isPanelOpen) {
                Column(
                    modifier = Modifier
                        .width(320.dp)
                        .fillMaxHeight()
                        .background(Color(0xFFEEEEEE))
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Editor Avatar",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Dropdown Pilih Komponen
                    Text(
                        text = "Pilih Komponen:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    ComponentSelector(
                        selectedComponent = selectedComponent,
                        onComponentSelected = { selectedComponent = it }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Kontrol untuk komponen yang dipilih
                    val currentTransform = transforms[selectedComponent]!!

                    // Kontrol Posisi X
                    Text(
                        text = "Posisi X: ${currentTransform.offsetX.toInt()}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Slider(
                        value = currentTransform.offsetX,
                        onValueChange = {
                            transforms[selectedComponent] = currentTransform.copy(offsetX = it)
                        },
                        valueRange = -150f..150f,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Kontrol Posisi Y
                    Text(
                        text = "Posisi Y: ${currentTransform.offsetY.toInt()}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Slider(
                        value = currentTransform.offsetY,
                        onValueChange = {
                            transforms[selectedComponent] = currentTransform.copy(offsetY = it)
                        },
                        valueRange = -150f..150f,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Kontrol Ukuran
                    Text(
                        text = "Ukuran: ${String.format("%.2f", currentTransform.scale)}x",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Slider(
                        value = currentTransform.scale,
                        onValueChange = {
                            transforms[selectedComponent] = currentTransform.copy(scale = it)
                        },
                        valueRange = 0.5f..2f,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Tombol Reset
                    Button(
                        onClick = {
                            transforms[selectedComponent] = ComponentTransform()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF6B6B)
                        )
                    ) {
                        Text("Reset $selectedComponent")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Tombol Reset Semua
                    OutlinedButton(
                        onClick = {
                            transforms.keys.forEach { key ->
                                transforms[key] = ComponentTransform()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Reset Semua")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Info Nilai (untuk copy paste ke code)
                    Text(
                        text = "Nilai Transformasi:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            transforms.forEach { (name, transform) ->
                                Text(
                                    text = "$name:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "  X: ${transform.offsetX.toInt()}, Y: ${transform.offsetY.toInt()}, Scale: ${String.format("%.2f", transform.scale)}",
                                    fontSize = 11.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Tombol Toggle Panel (Floating)
        FloatingActionButton(
            onClick = { isPanelOpen = !isPanelOpen },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Text(
                text = if (isPanelOpen) "◀" else "▶",
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun ComponentSelector(
    selectedComponent: String,
    onComponentSelected: (String) -> Unit
) {
    val components = listOf("Wajah", "Mata", "Alis", "Hidung", "Mulut")
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedComponent)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            components.forEach { component ->
                DropdownMenuItem(
                    text = { Text(component) },
                    onClick = {
                        onComponentSelected(component)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun AvatarCompose(transforms: Map<String, ComponentTransform>) {
    Box(
        modifier = Modifier.size(300.dp),
        contentAlignment = Alignment.Center
    ) {
        // Layer 1: Wajah (base)
        AvatarComponent(
            drawableId = R.drawable.wajah,
            contentDescription = "Wajah",
            transform = transforms["Wajah"]!!
        )

        // Layer 2: Mata
        AvatarComponent(
            drawableId = R.drawable.mata,
            contentDescription = "Mata",
            transform = transforms["Mata"]!!
        )

        // Layer 3: Alis
        AvatarComponent(
            drawableId = R.drawable.alis,
            contentDescription = "Alis",
            transform = transforms["Alis"]!!
        )

        // Layer 4: Hidung
        AvatarComponent(
            drawableId = R.drawable.hidung,
            contentDescription = "Hidung",
            transform = transforms["Hidung"]!!
        )

        // Layer 5: Mulut
        AvatarComponent(
            drawableId = R.drawable.mulut,
            contentDescription = "Mulut",
            transform = transforms["Mulut"]!!
        )
    }
}

@Composable
fun AvatarComponent(
    drawableId: Int,
    contentDescription: String,
    transform: ComponentTransform
) {
    Image(
        painter = painterResource(id = drawableId),
        contentDescription = contentDescription,
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(
                translationX = transform.offsetX,
                translationY = transform.offsetY,
                scaleX = transform.scale,
                scaleY = transform.scale
            ),
        contentScale = ContentScale.Fit
    )
}