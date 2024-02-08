package ru.pochtabank.templates.viewModelFragment.src.app_package

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import ru.pochtabank.templates.core.utils.getModuleName
import ru.pochtabank.templates.models.BuildSettings

fun buildScreenFactoryKt(
    buildSettings: BuildSettings,
): String {
    return """package ${escapeKotlinIdentifier(buildSettings.packageName)}

import android.os.Bundle
import androidx.fragment.app.Fragment

object ${getModuleName(buildSettings.moduleData).replaceFirstChar(Char::uppercase)}ScreenFactory {
    fun create${buildSettings.entityName}Fragment(): Fragment {
        return ${buildSettings.entityName}Fragment().apply {
            arguments = ${buildSettings.entityName}Fragment.createExtras()
        }
    }
}
"""
}