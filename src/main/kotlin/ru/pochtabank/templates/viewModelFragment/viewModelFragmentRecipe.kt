package ru.pochtabank.templates.viewModelFragment

import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import ru.pochtabank.templates.core.mvvm.src.app_package.blankViewModelKt
import ru.pochtabank.templates.core.utils.getRouteStateModelName
import ru.pochtabank.templates.core.utils.getUiModelsPackageName
import ru.pochtabank.templates.core.utils.getUiStateModelName
import ru.pochtabank.templates.core.utils.uiModelsPathName
import ru.pochtabank.templates.viewModelFragment.res.layout.blankFragmentXml
import ru.pochtabank.templates.viewModelFragment.src.app_package.blankFragmentKt
import ru.pochtabank.templates.viewModelFragment.src.app_package.blankRoutingStateKt
import ru.pochtabank.templates.viewModelFragment.src.app_package.blankUiStateKt

fun RecipeExecutor.viewModelFragmentRecipe(
    buildSettings: BuildSettings,
) {
    val (projectData, srcOut, resOut) = buildSettings.moduleData

    addAllKotlinDependencies(buildSettings.moduleData)

    val fragmentClass = "${buildSettings.entityName}Fragment"
    val viewModelClass = "${buildSettings.entityName}ViewModel"
    val viewModelPath = srcOut.resolve("$viewModelClass.kt")

    save(
        blankViewModelKt(
            buildSettings = buildSettings,
        ),
        viewModelPath
    )
    save(
        blankFragmentKt(
            buildSettings = buildSettings,
        ),
        srcOut.resolve("$fragmentClass.kt")
    )

    if (buildSettings.isGenerateUiStateModel) {
        save(
            blankUiStateKt(
                className = getUiStateModelName(buildSettings.entityName),
                packageName = getUiModelsPackageName(buildSettings.packageName),
            ),
            srcOut.resolve("$uiModelsPathName/${getUiStateModelName(buildSettings.entityName)}.kt")
        )
    }
    if (buildSettings.isGenerateRoutingStateModel) {
        save(
            blankRoutingStateKt(
                className = getRouteStateModelName(buildSettings.entityName),
                packageName = getUiModelsPackageName(buildSettings.packageName),
            ),
            srcOut.resolve("$uiModelsPathName/${getRouteStateModelName(buildSettings.entityName)}.kt")
        )
    }
    save(
        blankFragmentXml(),
        resOut.resolve("layout/${buildSettings.layoutName}.xml")
    )
    open(viewModelPath)
}