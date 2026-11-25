package com.example.model

enum class ServiceCategory(val title: String, val iconName: String) {
    ILUMINACAO("Iluminação Pública", "lightbulb"),
    ASFALTO_BURACO("Buracos na Pista / Asfalto", "road"),
    LIMPEZA_ENTULHO("Limpeza & Coleta de Entulho", "delete"),
    ARVORES_PRACAS("Poda de Árvores & Praças", "park"),
    AGUA_ESGOTO("Vazamento de Água / Esgoto", "water"),
    SINALIZACAO("Sinalização de Trânsito", "traffic"),
    CALCADAS("Acessibilidade & Calçadas", "accessible"),
    VIGILANCIA_ZOONOSES("Dengue / Controle Zoonoses", "pest")
}

enum class ServiceStatus(val label: String, val colorHex: Long) {
    PENDENTE("Em Análise", 0xFFE27C00),
    EM_ANDAMENTO("Em Execução", 0xFF0288D1),
    CONCLUIDO("Resolvido", 0xFF2E7D32),
    CANCELADO("Cancelado", 0xFF757575)
}

data class ServiceRequest(
    val id: String,
    val protocolNumber: String,
    val category: ServiceCategory,
    val description: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val photoUri: String? = null,
    val dateFormatted: String,
    val status: ServiceStatus,
    val statusUpdateNote: String = "Sua solicitação foi registrada no sistema municipal.",
    val feedbackRating: Int? = null,
    val feedbackComment: String? = null
)

data class NewsItem(
    val id: String,
    val title: String,
    val category: String,
    val summary: String,
    val date: String,
    val important: Boolean = false
)

data class JobOpening(
    val id: String,
    val title: String,
    val company: String,
    val salary: String,
    val location: String,
    val type: String, // CLT, Estágio, etc.
    val requirements: String
)

data class CityEvent(
    val id: String,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val category: String,
    val description: String
)

data class CitizenProfile(
    val fullName: String,
    val cpf: String,
    val email: String,
    val phone: String,
    val neighborhood: String,
    val address: String
)

data class UserMessage(
    val id: String,
    val sender: String,
    val message: String,
    val timestamp: String,
    val isFromSecretary: Boolean
)
