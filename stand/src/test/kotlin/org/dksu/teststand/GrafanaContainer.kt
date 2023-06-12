package org.dksu.teststand

import com.google.gson.Gson
import mu.KotlinLogging
import org.testcontainers.containers.BindMode

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class GrafanaContainer : GenericContainerWithFixedPort<GrafanaContainer>(
    "grafana/grafana"
) {
    companion object {
        val logger = KotlinLogging.logger {}
    }

    init {
        withNetwork(testStandNetwork)
        withNetworkAliases("grafana")
        withExposedPorts(3000)
        withEnv("GF_SERVER_DOMAIN", "localhost")
        withEnv("GF_SERVER_HTTP_PORT", "3000")
        withEnv("GF_SERVER_PROTOCOL", "http")
        withClasspathResourceMapping(
            "grafana.ini",
            "/etc/grafana/grafana.ini",
            BindMode.READ_ONLY
        )
    }

    val client = HttpClient.newBuilder().build()
    val mapper = Gson()

    fun startContainer(): GrafanaContainer {
        super.start()
        return this
    }

    fun getUrl() = "http://localhost:${firstMappedPort}"

    fun addPrometheusDataSource(): String {
        val request = HttpRequest.newBuilder(
            URI("${getUrl()}/api/datasources")
        ).headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(createDataSourceRequestJson))
            .build()
        val responseJson = client.send(request, HttpResponse.BodyHandlers.ofString())
        val response = mapper.fromJson(responseJson.body(), DataSourceResponseWrapper::class.java)
        return response.datasource.uid
    }

    fun addDashboard(uid: String): String {
        val dashboardsJsonRequest = Thread.currentThread().contextClassLoader
            .getResourceAsStream("grafanaDashboardMicrometer.json")
            .reader().readText().replace("{{[[datasource_id]]}}", uid)
        val request = HttpRequest
            .newBuilder(URI("${getUrl()}/api/dashboards/import"))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(dashboardsJsonRequest))
            .build()
        val responseJson = client.send(request, HttpResponse.BodyHandlers.ofString())
        val response = mapper.fromJson(responseJson.body(), DashboardResponse::class.java)
        println("OK")
        return response.importedUrl
    }
}

const val createDataSourceRequestJson = """
{
	"orgId": 1,
	"name": "Prometheus",
	"type": "prometheus",
	"typeLogoUrl": "",
	"access": "proxy",
	"url": "http://prometheus:9090",
	"user": "",
	"database": "",
	"basicAuth": false,
	"basicAuthUser": "",
	"withCredentials": false,
	"isDefault": true,
	"jsonData": {
		"httpMethod": "POST",
		"timeInterval": "1s"
	},
	"secureJsonFields": {},
	"version": 1,
	"readOnly": false,
	"accessControl": {
		"alert.instances.external:read": true,
		"alert.instances.external:write": true,
		"alert.notifications.external:read": true,
		"alert.notifications.external:write": true,
		"alert.rules.external:read": true,
		"alert.rules.external:write": true,
		"datasources.id:read": true,
		"datasources:delete": true,
		"datasources:query": true,
		"datasources:read": true,
		"datasources:write": true
	}
}
"""

class DataSourceResponseWrapper(
    val datasource: DataSourceResponse
)

class DataSourceResponse(
    val id: String,
    val uid: String,
)

class DashboardResponse(
    val uid: String,
    val importedUrl: String,
)