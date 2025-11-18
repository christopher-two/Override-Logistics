package org.override.logistics.features.haulier.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Camera
import compose.icons.fontawesomeicons.solid.Check
import compose.icons.fontawesomeicons.solid.CheckCircle
import compose.icons.fontawesomeicons.solid.Map
import compose.icons.fontawesomeicons.solid.Route
import compose.icons.fontawesomeicons.solid.Tasks
import org.koin.androidx.compose.koinViewModel
import org.override.logistics.core.common.RoutesApp
import org.override.logistics.core.ui.BrutalistButton
import org.override.logistics.core.ui.theme.OverrideLogisticsTheme
import org.override.logistics.features.haulier.presentation.screens.dashboard.DashboardRoot

// --- MODELOS DE DATOS ---

enum class ShipmentStatus(val displayName: String) {
    ASSIGNED("ASIGNADO"),
    LOADING("EN CARGA"),
    IN_TRANSIT("EN RUTA"),
    DELIVERED("ENTREGADO")
}

@Composable
private fun ShipmentStatus.toColor(): Color = when (this) {
    ShipmentStatus.ASSIGNED -> MaterialTheme.colorScheme.secondaryContainer
    ShipmentStatus.LOADING -> MaterialTheme.colorScheme.primaryContainer
    ShipmentStatus.IN_TRANSIT -> MaterialTheme.colorScheme.tertiaryContainer
    ShipmentStatus.DELIVERED -> MaterialTheme.colorScheme.surfaceVariant
}

@Composable
private fun ShipmentStatus.onColor(): Color = when (this) {
    ShipmentStatus.ASSIGNED -> MaterialTheme.colorScheme.onSecondaryContainer
    ShipmentStatus.LOADING -> MaterialTheme.colorScheme.onPrimaryContainer
    ShipmentStatus.IN_TRANSIT -> MaterialTheme.colorScheme.onTertiaryContainer
    ShipmentStatus.DELIVERED -> MaterialTheme.colorScheme.onSurfaceVariant
}

data class Shipment(
    val id: String,
    val address: String,
    val status: ShipmentStatus
)

data class ChecklistItem(
    val id: Int,
    val text: String,
    val isChecked: Boolean
)
// --- PANTALLA PRINCIPAL ---

/**
 * Screen principal para el módulo de transportista.
 *
 * @param assignedShipments Lista de embarques asignados para el día.
 * @param checklistItems Lista de items para el checklist pre-viaje.
 * @param onShowShipmentDetails Lambda para ver los detalles de un embarque.
 * @param onAcceptShipmentWithSignature Lambda que se invoca cuando el chofer firma para aceptar la mercancía.
 * @param onOpenInMaps Lambda para abrir la dirección en una app de mapas.
 * @param onChecklistItemToggle Lambda para cambiar el estado de un item del checklist.
 * @param onStartRoute Lambda que se invoca al confirmar el checklist y empezar la ruta.
 * @param onConfirmDelivery Lambda que se invoca al confirmar una entrega con firma y foto.
 * @param onShowTripHistory Lambda para navegar al historial de viajes.
 */
@Composable
fun CarrierDashboardScreen(
    assignedShipments: List<Shipment>,
    checklistItems: List<ChecklistItem>,
    onShowShipmentDetails: (shipmentId: String) -> Unit,
    onAcceptShipmentWithSignature: (shipmentId: String) -> Unit,
    onOpenInMaps: (address: String) -> Unit,
    onChecklistItemToggle: (itemId: Int) -> Unit,
    onStartRoute: () -> Unit,
    onConfirmDelivery: (shipmentId: String, customerSignature: Boolean, photoTaken: Boolean) -> Unit,
    onShowTripHistory: () -> Unit
) {
    var currentView by remember { mutableStateOf(RoutesApp.Haulier.DASHBOARD) }
    var selectedShipment by remember { mutableStateOf<Shipment?>(null) }
    val navController = rememberNavController()

    LaunchedEffect(currentView) {
        val currentRoute = navController.currentDestination?.route
        if (currentRoute != currentView) {
            navController.navigate(currentView) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    Scaffold(
        topBar = {
            BrutalistTopBar(
                title = when (currentView) {
                    RoutesApp.Haulier.DASHBOARD -> "MIS VIAJES DE HOY"
                    RoutesApp.Haulier.CHECKLIST -> "CHECKLIST PRE-VIAJE"
                    RoutesApp.Haulier.DELIVERY -> "ENTREGA: #${selectedShipment?.id}"
                    else -> "PANEL DE CHOFER"
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            NotificationBanner(
                text = "¡Nuevo embarque asignado en tu ruta!",
                isVisible = true
            )
            NavHost(
                navController = navController,
                startDestination = RoutesApp.Haulier.DASHBOARD
            ) {
                composable(RoutesApp.Haulier.DASHBOARD) {
                    DashboardRoot(koinViewModel())
                }
                composable(RoutesApp.Haulier.CHECKLIST) {
                    PreTripChecklistView(
                        items = checklistItems,
                        onToggle = onChecklistItemToggle,
                        onConfirm = {
                            onStartRoute()
                            currentView = "delivery"
                        }
                    )
                }
                composable(RoutesApp.Haulier.DELIVERY) {
                    selectedShipment?.let {
                        DeliveryProcessView(
                            shipment = it,
                            onConfirm = { signature, photo ->
                                onConfirmDelivery(it.id, signature, photo)
                                currentView = "dashboard" // Vuelve al panel
                            },
                            onAcceptCustody = { onAcceptShipmentWithSignature(it.id) }
                        )
                    }
                }
            }
        }
    }
}


// --- VISTAS DE LA PANTALLA ---

@Composable
private fun PreTripChecklistView(
    items: List<ChecklistItem>,
    onToggle: (Int) -> Unit,
    onConfirm: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                ChecklistItemRow(item = item, onToggle = { onToggle(item.id) })
            }
        }
        Spacer(Modifier.height(16.dp))
        BrutalistButton(
            text = "CONFIRMAR Y EMPEZAR RUTA",
            onClick = onConfirm,
            icon = FontAwesomeIcons.Solid.Route,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            enabled = items.all { it.isChecked } // Solo se activa si todo está chequeado
        )
    }
}

@Composable
private fun DeliveryProcessView(
    shipment: Shipment,
    onConfirm: (customerSignature: Boolean, photoTaken: Boolean) -> Unit,
    onAcceptCustody: () -> Unit
) {
    var photoTaken by remember { mutableStateOf(false) }
    var custodyAccepted by remember { mutableStateOf(false) }
    var customerSigned by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // 1. Aceptar custodia (al inicio)
            if (!custodyAccepted) {
                SignatureSection(
                    title = "ACEPTAR CUSTODIA DE MERCANCÍA",
                    subtitle = "Firma para confirmar la recepción de la carga.",
                    buttonText = "ACEPTAR Y FIRMAR",
                    onConfirm = {
                        onAcceptCustody()
                        custodyAccepted = true
                    }
                )
            } else {
                // 2. Proceso de entrega al cliente
                Text(
                    "ENTREGA AL CLIENTE",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black
                )
                Spacer(Modifier.height(16.dp))

                // Tomar foto
                BrutalistButton(
                    text = if (photoTaken) "FOTO TOMADA" else "TOMAR FOTO DE EVIDENCIA",
                    onClick = { photoTaken = !photoTaken },
                    icon = if (photoTaken) FontAwesomeIcons.Solid.CheckCircle else FontAwesomeIcons.Solid.Camera,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (photoTaken) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.secondary,
                        contentColor = if (photoTaken) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSecondary
                    )
                )
                Spacer(Modifier.height(24.dp))
                // Firma del cliente
                SignatureSection(
                    title = "FIRMA DE RECEPCIÓN DEL CLIENTE",
                    subtitle = "El cliente debe firmar para confirmar la entrega.",
                    buttonText = "CLIENTE HA FIRMADO",
                    onConfirm = { customerSigned = true },
                    isConfirmed = customerSigned
                )
            }
        }

        BrutalistButton(
            text = "CONFIRMAR ENTREGA FINAL",
            onClick = { onConfirm(customerSigned, photoTaken) },
            icon = FontAwesomeIcons.Solid.Tasks,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            enabled = custodyAccepted && customerSigned && photoTaken
        )
    }
}

// --- COMPONENTES REUTILIZABLES DE ESTILO BRUTALISTA ---

@Composable
fun ShipmentCard(shipment: Shipment, onViewDetails: () -> Unit, onOpenInMaps: () -> Unit) {
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(shipment.status.toColor())
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = shipment.status.displayName,
                    color = shipment.status.onColor(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
            Spacer(Modifier.height(8.dp))
            Text("EMBARQUE #${shipment.id}", fontWeight = FontWeight.Black, fontSize = 20.sp)
            Text(
                shipment.address,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                BrutalistButton(
                    text = "VERIFICAR",
                    onClick = onViewDetails,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                BrutalistButton(
                    text = "MAPA",
                    onClick = onOpenInMaps,
                    icon = FontAwesomeIcons.Solid.Map,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun ChecklistItemRow(item: ChecklistItem, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                2.dp,
                if (item.isChecked) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.5f
                )
            )
            .background(if (item.isChecked) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)
            .clickable { onToggle() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(item.text, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = { onToggle() },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

@Composable
private fun SignatureSection(
    title: String,
    subtitle: String,
    buttonText: String,
    onConfirm: () -> Unit,
    isConfirmed: Boolean = false
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, textAlign = TextAlign.Center)
        Text(
            subtitle,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .border(2.dp, MaterialTheme.colorScheme.outline)
                .background(if (isConfirmed) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            if (isConfirmed) {
                Icon(
                    FontAwesomeIcons.Solid.Check,
                    "Firmado",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(48.dp)
                )
            } else {
                Text(
                    "ESPACIO DE FIRMA",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        BrutalistButton(
            text = if (isConfirmed) "FIRMADO" else buttonText,
            onClick = onConfirm,
            enabled = !isConfirmed,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isConfirmed) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.tertiary,
                contentColor = if (isConfirmed) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onTertiary,
            )
        )
    }
}

@Composable
private fun NotificationBanner(text: String, isVisible: Boolean) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .border(2.dp, MaterialTheme.colorScheme.outline)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BrutalistTopBar(title: String) {
    TopAppBar(
        title = {
            Text(
                title,
                fontWeight = FontWeight.Black,
                fontSize = 25.sp,
                letterSpacing = 2.sp
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.border(2.dp, MaterialTheme.colorScheme.outline)
    )
}

@Composable
fun BrutalistButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        enabled = enabled,
        colors = colors,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(2.dp, MaterialTheme.colorScheme.outline)
    ) {
        icon?.let {
            Icon(it, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}


// --- PREVIEW ---

@Preview(showBackground = true)
@Composable
fun CarrierDashboardScreen_Preview() {
    val sampleShipments = remember {
        mutableStateListOf(
            Shipment("Emb-112", "Av. Reforma 222, CDMX", ShipmentStatus.ASSIGNED),
            Shipment("Emb-113", "Calle Falsa 123, Springfield", ShipmentStatus.LOADING)
        )
    }
    val checklistItems = remember {
        mutableStateListOf(
            ChecklistItem(1, "Revisar neumáticos", false),
            ChecklistItem(2, "Verificar luces", false),
            ChecklistItem(3, "Niveles de aceite y agua", false),
            ChecklistItem(4, "Documentación completa", false)
        )
    }

    OverrideLogisticsTheme(
        darkTheme = true
    ) { // Wrapper para que el Preview use los colores de M3
        CarrierDashboardScreen(
            assignedShipments = sampleShipments,
            checklistItems = checklistItems,
            onShowShipmentDetails = { /* Logica */ },
            onAcceptShipmentWithSignature = { /* Logica */ },
            onOpenInMaps = { /* Logica */ },
            onChecklistItemToggle = { id ->
                val index = checklistItems.indexOfFirst { it.id == id }
                if (index != -1) {
                    checklistItems[index] =
                        checklistItems[index].copy(isChecked = !checklistItems[index].isChecked)
                }
            },
            onStartRoute = { /* Logica */ },
            onConfirmDelivery = { _, _, _ -> /* Logica */ },
            onShowTripHistory = { /* Logica */ }
        )
    }
}

