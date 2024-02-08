package ru.pochtabank.templates.core.utils

import com.android.tools.idea.wizard.template.ModuleTemplateData

val uiModelsPathName = "models"
val presentationPackageEndWith = "presentation.ui"

fun getUiModelsPackageName(uiPackageName: String): String {
    return "$uiPackageName.$uiModelsPathName"
}

fun getUiStateModelName(entityName: String): String {
    return "${entityName}UiState"
}

fun getRouteStateModelName(entityName: String): String {
    return "${entityName}RouteState"
}

fun getRootPackageName(packageName: String): String {
    //Пока не разобрался как это можно сделать правильно. Временно сделал так.
    return packageName.split(".")
        .take(3)
        .joinToString(separator = ".")
}

fun getRootPackageName(data: ModuleTemplateData): String {
    val rootPackageName = data.projectTemplateData.applicationPackage
    if (!rootPackageName.isNullOrBlank()) return rootPackageName

    //Пока не разобрался как это можно сделать стандартным образом. Временно сделал так.
    //Должен возвращать название модуля
    val moduleName = getModuleName(data = data)
    val delimiter = ".${moduleName}"
    return if (data.packageName.contains(delimiter, true)) {
        val delimiterLength = delimiter.length
        data.packageName.substring(
            0,
            data.packageName.indexOf(delimiter) + delimiterLength
        )
    } else {
        getRootPackageName(packageName = data.packageName)
    }
}

fun getModuleName(data: ModuleTemplateData): String {
    val root = data.projectTemplateData.applicationPackage ?: data.name
    return root
        .split(".")
        .last()
}

fun getPresentationPackageName(targetPackageName: String): String {
    return "$targetPackageName.$presentationPackageEndWith"
}