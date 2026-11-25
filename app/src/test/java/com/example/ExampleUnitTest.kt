package com.example

import com.example.data.CityRepository
import com.example.model.ServiceCategory
import com.example.model.ServiceStatus
import com.example.viewmodel.AppScreen
import com.example.viewmodel.CityViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun `test initial state and categories`() {
        val repo = CityRepository()
        val viewModel = CityViewModel(repo)

        assertEquals(AppScreen.HOME, viewModel.currentScreen.value)
        assertEquals(3, viewModel.requests.value.size)
        assertEquals(4, viewModel.news.value.size)
        assertEquals(4, viewModel.jobs.value.size)
        assertEquals(3, viewModel.events.value.size)
    }

    @Test
    fun `test service request creation and tracking`() {
        val repo = CityRepository()
        val viewModel = CityViewModel(repo)

        val initialCount = viewModel.requests.value.size

        viewModel.submitServiceRequest(
            category = ServiceCategory.ILUMINACAO,
            description = "Lâmpada queimada na esquina",
            address = "Rua das Flores, 100",
            latitude = -23.55,
            longitude = -46.63,
            photoUri = null
        )

        val updatedRequests = viewModel.requests.value
        assertEquals(initialCount + 1, updatedRequests.size)

        val created = updatedRequests.first()
        assertTrue(created.protocolNumber.startsWith("2026/"))
        assertEquals("Lâmpada queimada na esquina", created.description)
        assertEquals(ServiceStatus.PENDENTE, created.status)
        assertEquals(AppScreen.REQUEST_DETAIL, viewModel.currentScreen.value)
    }

    @Test
    fun `test service evaluation submission`() {
        val repo = CityRepository()
        val viewModel = CityViewModel(repo)

        val target = viewModel.requests.value.first()
        viewModel.submitRating(target.id, 5, "Serviço excelente e pontual!")

        val updated = viewModel.requests.value.first { it.id == target.id }
        assertEquals(5, updated.feedbackRating)
        assertEquals("Serviço excelente e pontual!", updated.feedbackComment)
    }

    @Test
    fun `test messaging citizen ouvidoria`() {
        val repo = CityRepository()
        val viewModel = CityViewModel(repo)

        val initialMsgCount = viewModel.messages.value.size
        viewModel.sendCitizenMessage("Preciso de informações sobre a coleta seletiva.")

        val updatedMsgs = viewModel.messages.value
        assertEquals(initialMsgCount + 1, updatedMsgs.size)
        assertEquals("Preciso de informações sobre a coleta seletiva.", updatedMsgs.last().message)
    }
}
