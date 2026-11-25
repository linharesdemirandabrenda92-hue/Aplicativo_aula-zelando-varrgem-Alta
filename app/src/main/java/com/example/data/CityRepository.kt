package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class CityRepository {

    private val _requests = MutableStateFlow<List<ServiceRequest>>(
        listOf(
            ServiceRequest(
                id = "req-1",
                protocolNumber = "2026/09412",
                category = ServiceCategory.ILUMINACAO,
                description = "Poste com lâmpada queimada piscando há 4 dias, deixando o trecho escuro e perigoso.",
                address = "Av. Brasil, nº 1420 - Centro",
                latitude = -23.5505,
                longitude = -46.6333,
                photoUri = null,
                dateFormatted = "09/11/2026",
                status = ServiceStatus.EM_ANDAMENTO,
                statusUpdateNote = "Equipe técnica de iluminação pública enviada ao local.",
                feedbackRating = null,
                feedbackComment = null
            ),
            ServiceRequest(
                id = "req-2",
                protocolNumber = "2026/08901",
                category = ServiceCategory.ASFALTO_BURACO,
                description = "Buraco fundo no cruzamento danificando pneus de veículos e ônibus escolares.",
                address = "Rua das Palmeiras, esquina com Rua 7 - Jardim Esperança",
                latitude = -23.5580,
                longitude = -46.6410,
                photoUri = null,
                dateFormatted = "04/11/2026",
                status = ServiceStatus.CONCLUIDO,
                statusUpdateNote = "Operação tapa-buraco executada com sucesso.",
                feedbackRating = 5,
                feedbackComment = "Serviço rápido e bem asfaltado! Parabéns à equipe."
            ),
            ServiceRequest(
                id = "req-3",
                protocolNumber = "2026/10123",
                category = ServiceCategory.LIMPEZA_ENTULHO,
                description = "Descarte irregular de restos de poda e entulho na calçada próxima à escola.",
                address = "Praça dos Ipês, próx. nº 85 - Vila Nova",
                latitude = -23.5420,
                longitude = -46.6280,
                photoUri = null,
                dateFormatted = "10/11/2026",
                status = ServiceStatus.PENDENTE,
                statusUpdateNote = "Aguardando cronograma de coleta de caçambas pesadas."
            )
        )
    )
    val requests: StateFlow<List<ServiceRequest>> = _requests.asStateFlow()

    private val _news = MutableStateFlow<List<NewsItem>>(
        listOf(
            NewsItem(
                id = "news-1",
                title = "Campanha de Vacinação e Saúde Preventiva neste Sábado",
                category = "Saúde Pública",
                summary = "Todas as UBSs estarão abertas das 8h às 17h para atualização da caderneta e exames de rotina.",
                date = "Hoje, 09:30",
                important = true
            ),
            NewsItem(
                id = "news-2",
                title = "Nova Linha de Ônibus Expresso Interbairros Entra em Operação",
                category = "Transporte & Mobilidade",
                summary = "A linha 402 ligará a Zona Norte ao Polo Industrial com intervalos de 15 minutos em horários de pico.",
                date = "Ontem",
                important = false
            ),
            NewsItem(
                id = "news-3",
                title = "Prefeitura Abre 400 Vagas para Cursos Profissionalizantes Gratuitos",
                category = "Educação & Emprego",
                summary = "Capacitação em Tecnologia, Logística e Gestão em parceria com o Centro de Inovação Municipal.",
                date = "08/11/2026",
                important = false
            ),
            NewsItem(
                id = "news-4",
                title = "Obras de Drenagem e Pavimentação no Bairro São Judas",
                category = "Obras Urbanas",
                summary = "Investimento histórico contra enchentes e melhoria de 12 ruas e avenidas principais.",
                date = "06/11/2026",
                important = false
            )
        )
    )
    val news: StateFlow<List<NewsItem>> = _news.asStateFlow()

    private val _jobs = MutableStateFlow<List<JobOpening>>(
        listOf(
            JobOpening(
                id = "job-1",
                title = "Auxiliar Administrativo e Atendimento",
                company = "Posto de Atendimento ao Cidadão (PAT)",
                salary = "R$ 2.450,00 + Benefícios",
                location = "Centro Cívico",
                type = "CLT - Presencial",
                requirements = "Ensino Médio completo, noções de informática e bom relacionamento interpessoal."
            ),
            JobOpening(
                id = "job-2",
                title = "Eletricista de Manutenção Predial e Urbana",
                company = "Consórcio de Infraestrutura Urbana",
                salary = "R$ 3.800,00 + Periculosidade",
                location = "Distrito Industrial",
                type = "CLT - Escala",
                requirements = "NR10 ativa, CNH B e experiência em redes de baixa e média tensão."
            ),
            JobOpening(
                id = "job-3",
                title = "Técnico em Meio Ambiente / Jardineiro",
                company = "Secretaria Municipal de Meio Ambiente",
                salary = "R$ 2.890,00",
                location = "Parque Ecológico Municipal",
                type = "Processo Seletivo",
                requirements = "Curso técnico ou experiência comprovada em arborização e paisagismo."
            ),
            JobOpening(
                id = "job-4",
                title = "Operador de Máquinas Pesadas (Retroescavadeira)",
                company = "Construtora Municipal Metropolitana",
                salary = "R$ 4.200,00",
                location = "Obras Públicas",
                type = "CLT",
                requirements = "CNH D ou E, certificação de operador e vivência em terraplanagem."
            )
        )
    )
    val jobs: StateFlow<List<JobOpening>> = _jobs.asStateFlow()

    private val _events = MutableStateFlow<List<CityEvent>>(
        listOf(
            CityEvent(
                id = "event-1",
                title = "Feira Gastronômica & Artesanato no Bosque",
                date = "Sábado, 15 de Novembro",
                time = "10:00 às 20:00",
                location = "Bosque Central da Cidade",
                category = "Cultura e Lazer",
                description = "Mais de 50 expositores locais, comidas típicas regionais, música ao vivo e espaço kids."
            ),
            CityEvent(
                id = "event-2",
                title = "Passeio Ciclístico da Família e Saúde",
                date = "Domingo, 16 de Novembro",
                time = "07:30",
                location = "Saída da Praça Central",
                category = "Esporte e Bem-Estar",
                description = "Percurso de 10km com hidratação, sorteio de bicicletas e apoio de trânsito em todo o trajeto."
            ),
            CityEvent(
                id = "event-3",
                title = "Mutirão de Empreendedorismo e Microcrédito",
                date = "Quarta, 19 de Novembro",
                time = "09:00 às 16:00",
                location = "Teatro Municipal",
                category = "Negócios & Cidadania",
                description = "Orientação para MEI, emissão de alvarás rápidos e consultoria do Sebrae."
            )
        )
    )
    val events: StateFlow<List<CityEvent>> = _events.asStateFlow()

    private val _messages = MutableStateFlow<List<UserMessage>>(
        listOf(
            UserMessage(
                id = "msg-1",
                sender = "Secretaria de Obras e Serviços",
                message = "Olá Brenda! Seu protocolo #2026/08901 (tapa-buraco na Rua das Palmeiras) foi concluído com sucesso. Por favor, avalie a qualidade do serviço executado.",
                timestamp = "05/11/2026 14:15",
                isFromSecretary = true
            ),
            UserMessage(
                id = "msg-2",
                sender = "Brenda Miranda",
                message = "Muito obrigada pelo retorno ágil! Ficou ótimo.",
                timestamp = "05/11/2026 14:30",
                isFromSecretary = false
            ),
            UserMessage(
                id = "msg-3",
                sender = "Ouvidoria Geral do Município",
                message = "Aviso: Neste fim de semana haverá manutenção programada na rede de abastecimento no Centro das 23h às 05h.",
                timestamp = "08/11/2026 10:00",
                isFromSecretary = true
            )
        )
    )
    val messages: StateFlow<List<UserMessage>> = _messages.asStateFlow()

    private val _profile = MutableStateFlow(
        CitizenProfile(
            fullName = "Brenda Linhares de Miranda",
            cpf = "345.***.***-82",
            email = "linharesdemirandabrenda92@gmail.com",
            phone = "(11) 98765-4321",
            neighborhood = "Jardim das Flores",
            address = "Rua dos Girassóis, 240, Apto 42"
        )
    )
    val profile: StateFlow<CitizenProfile> = _profile.asStateFlow()

    fun addServiceRequest(
        category: ServiceCategory,
        description: String,
        address: String,
        latitude: Double,
        longitude: Double,
        photoUri: String?
    ): ServiceRequest {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val randomNum = (10000..99999).random()
        val newReq = ServiceRequest(
            id = UUID.randomUUID().toString(),
            protocolNumber = "2026/$randomNum",
            category = category,
            description = description,
            address = address,
            latitude = latitude,
            longitude = longitude,
            photoUri = photoUri,
            dateFormatted = dateFormat.format(Date()),
            status = ServiceStatus.PENDENTE,
            statusUpdateNote = "Solicitação enviada com sucesso à equipe municipal para vistoria técnica."
        )
        _requests.value = listOf(newReq) + _requests.value
        return newReq
    }

    fun submitEvaluation(requestId: String, rating: Int, comment: String) {
        _requests.value = _requests.value.map { req ->
            if (req.id == requestId) {
                req.copy(feedbackRating = rating, feedbackComment = comment)
            } else {
                req
            }
        }
    }

    fun sendMessage(text: String) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val newMsg = UserMessage(
            id = UUID.randomUUID().toString(),
            sender = _profile.value.fullName,
            message = text,
            timestamp = dateFormat.format(Date()),
            isFromSecretary = false
        )
        _messages.value = _messages.value + newMsg
    }

    fun updateProfile(newProfile: CitizenProfile) {
        _profile.value = newProfile
    }
}
