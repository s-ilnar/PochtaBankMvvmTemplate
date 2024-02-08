package ru.pochtabank.templates.viewModelFragment

import com.android.tools.idea.wizard.template.Category
import com.android.tools.idea.wizard.template.CheckBoxWidget
import com.android.tools.idea.wizard.template.Constraint
import com.android.tools.idea.wizard.template.EnumWidget
import com.android.tools.idea.wizard.template.FormFactor
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageNameWidget
import com.android.tools.idea.wizard.template.TemplateData
import com.android.tools.idea.wizard.template.TextFieldWidget
import com.android.tools.idea.wizard.template.WizardUiContext
import com.android.tools.idea.wizard.template.stringParameter
import com.android.tools.idea.wizard.template.template
import ru.pochtabank.templates.core.utils.Constants
import ru.pochtabank.templates.core.utils.getModuleName
import ru.pochtabank.templates.core.utils.getPresentationPackageName
import ru.pochtabank.templates.core.utils.getRootPackageName
import ru.pochtabank.templates.core.utils.presentationPackageEndWith
import ru.pochtabank.templates.models.BuildSettings
import ru.pochtabank.templates.viewModelFragment.builders.entityName
import ru.pochtabank.templates.viewModelFragment.builders.generateRoutingModel
import ru.pochtabank.templates.viewModelFragment.builders.generateUiStateModel
import ru.pochtabank.templates.viewModelFragment.builders.layoutName
import ru.pochtabank.templates.viewModelFragment.builders.visibilityType


val mvvmFragmentSetupTemplate
    get() = template {
        name = "ПБ Fragment + ViewModel"
        description = "Создает Fragment c ViewModel"
        minApi = Constants.MIN_SDK
        category = Category.Fragment
        formFactor = FormFactor.Mobile
        screens = listOf(
            WizardUiContext.FragmentGallery,
            WizardUiContext.MenuEntry,
        )

        val packageNameParam = stringParameter {
            name = "Имя пакета"
            visible = { !isNewModule }
            default = "ru.pochtabank.myapp"
            constraints = listOf(Constraint.PACKAGE)
            suggest = {
                generateRealPackageName(
                    packageName = packageName,
                    entityName = entityName.value,
                )
            }
        }

        widgets(
            TextFieldWidget(entityName),
            TextFieldWidget(layoutName),
            PackageNameWidget(packageNameParam),
            CheckBoxWidget(generateRoutingModel),
            CheckBoxWidget(generateUiStateModel),
            EnumWidget(visibilityType)
        )

        recipe = { data: TemplateData ->
            val packageName = packageNameParam.value
            val entityNameValue = entityName.value.replaceFirstChar(Char::uppercaseChar)
            data as ModuleTemplateData

            viewModelFragmentRecipe(
                BuildSettings(
                    moduleData = data,
                    packageName = packageName,
                    entityName = entityNameValue,
                    layoutName = getLayoutName(
                        data = data,
                        layoutName = layoutName.value,
                    ),
                    isGenerateUiStateModel = generateUiStateModel.value,
                    isGenerateRoutingStateModel = generateRoutingModel.value,
                    visibilityType = visibilityType.value,
                )
            )
        }
    }

private fun generateRealPackageName(packageName: String, entityName: String): String {
    //это сделано для того, чтобы если разработчик создает сущность выбрав корневой пакет,
    // то чтобы внутри пакета создалась правильная структура пакетов
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

    return if (packageName == getRootPackageName(packageName = packageName)) {
        getPresentationPackageName(targetPackageName = packageName) + entityPackageSuffix
    } else if (packageName.endsWith(presentationPackageEndWith)) {
        "$packageName$entityPackageSuffix"
    } else if (packageName.endsWith("presentation")) {
        "${packageName.substringBeforeLast(".presentation")}.${presentationPackageEndWith}$entityPackageSuffix"
    } else {
        packageName
    }
}

private fun getLayoutName(data: ModuleTemplateData, layoutName: String): String {
    val moduleName = getModuleName(data = data)
    return "${moduleName.lowercase()}_${layoutName}"
}
