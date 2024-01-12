package ru.pochtabank.templates.core.utils

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

fun getPresentationPackageName(targetPackageName: String): String {
    return "$targetPackageName.$presentationPackageEndWith"
}