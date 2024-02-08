package ru.pochtabank.templates.viewModelFragment.src.app_package

import ru.pochtabank.templates.models.BuildSettings

fun buildScreenFactoryMethodKt(
    buildSettings: BuildSettings,
): String {
    return """
        
        fun create${buildSettings.entityName}Fragment(): Fragment {
            return ${buildSettings.entityName}Fragment().apply {
                arguments = ${buildSettings.entityName}Fragment.createExtras()
            }
        }
    """
}