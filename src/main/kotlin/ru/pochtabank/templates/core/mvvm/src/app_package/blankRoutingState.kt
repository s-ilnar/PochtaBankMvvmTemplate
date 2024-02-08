package ru.pochtabank.templates.viewModelFragment.src.app_package

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import ru.pochtabank.templates.models.BuildSettings

fun blankRoutingStateKt(
    className: String,
    packageName: String,
    buildSettings: BuildSettings,
): String {
    return """package ${escapeKotlinIdentifier(packageName)}

${buildSettings.visibilityType.getClassVisibility()}sealed class $className {
    //Реализацию писать внутри фигурных скобок!
}
"""
}