package ru.pochtabank.templates.newModuleModule

import com.android.tools.idea.wizard.template.Category
import com.android.tools.idea.wizard.template.Constraint
import com.android.tools.idea.wizard.template.FormFactor
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageNameWidget
import com.android.tools.idea.wizard.template.TemplateConstraint
import com.android.tools.idea.wizard.template.WizardUiContext
import com.android.tools.idea.wizard.template.stringParameter
import com.android.tools.idea.wizard.template.template
import ru.pochtabank.templates.core.utils.Constants
import ru.pochtabank.templates.models.BuildSettings
import ru.pochtabank.templates.models.VisibilityType
import ru.pochtabank.templates.viewModelFragment.builders.entityName

val newModuleSetupTemplate
    get() = template {
        name = "Фича модуль ПБ"
        description = "Создает новый фича модуль"
        minApi = Constants.MIN_SDK
        category = Category.Other
        formFactor = FormFactor.Mobile
        screens = listOf(
            WizardUiContext.NewModule
        )
        constraints = listOf(TemplateConstraint.AndroidX, TemplateConstraint.Material3)

        val packageNameParam = stringParameter {
            name = "Имя пакета"
            visible = { isNewModule }
            default = "ru.pochtabank.newmodulename"
            constraints = listOf(Constraint.PACKAGE)
            suggest = {
                generateRealModulePackageName(
                    packageName = packageName,
                    entityName = entityName.value,
                )
            }
        }

        widgets(
            PackageNameWidget(packageNameParam),
        )

        recipe = { data ->
            val packageName = packageNameParam.value
            val entityNameValue = entityName.value.replaceFirstChar(Char::uppercaseChar)
            data as ModuleTemplateData
          //  if (packageName.count { it == '.' } < 3) throw Exception("Имя модуля должно быть вида ru.pochtabank.modulename")

            newModuleRecipe(
                BuildSettings(
                    moduleData = data,
                    packageName = packageName,
                    entityName = entityNameValue,
                    layoutName = "",
                    isGenerateUiStateModel = false,
                    isGenerateRoutingStateModel = false,
                    visibilityType = VisibilityType.PUBLIC_ALL,
                )
            )
        }
    }

private fun generateRealModulePackageName(packageName: String, entityName: String): String {
    val entityPackageSuffix = Regex("[^A-Za-z0-9 ]")
        .replace(entityName, "")
        .lowercase()
        .let {
            if (it.isNotBlank()) {
                ".$it"
            } else {
                ""
            }
        }

    return "$packageName$entityPackageSuffix"
}
