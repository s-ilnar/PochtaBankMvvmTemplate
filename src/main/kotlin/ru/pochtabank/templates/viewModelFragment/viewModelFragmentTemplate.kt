package ru.pochtabank.templates.viewModelFragment

import com.android.tools.idea.wizard.template.BooleanParameter
import com.android.tools.idea.wizard.template.Category
import com.android.tools.idea.wizard.template.CheckBoxWidget
import com.android.tools.idea.wizard.template.Constraint
import com.android.tools.idea.wizard.template.FormFactor
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageNameWidget
import com.android.tools.idea.wizard.template.TemplateData
import com.android.tools.idea.wizard.template.TextFieldWidget
import com.android.tools.idea.wizard.template.WizardUiContext
import com.android.tools.idea.wizard.template.booleanParameter
import com.android.tools.idea.wizard.template.classToResource
import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.fragmentToLayout
import com.android.tools.idea.wizard.template.stringParameter
import com.android.tools.idea.wizard.template.template
import org.jetbrains.kotlin.lombok.utils.decapitalize
import ru.pochtabank.templates.core.utils.getPresentationPackageName
import ru.pochtabank.templates.core.utils.getRootPackageName
import ru.pochtabank.templates.core.utils.presentationPackageEndWith
import ru.pochtabank.templates.mvvmPackageNameParameter

private const val MIN_SDK = 21

val mvvmFragmentSetupTemplate
    get() = template {
        name = "ПБ Fragment + ViewModel"
        description = "Создает Fragment c ViewModel"
        minApi = MIN_SDK
        category = Category.Fragment
        formFactor = FormFactor.Mobile
        screens = listOf(
            WizardUiContext.FragmentGallery,
            WizardUiContext.MenuEntry,
            WizardUiContext.NewModule
        )

        val entityName = stringParameter {
            name = "Название сущности"
            default = ""
            help =
                "Название сущности/фичи (например, Timeline -> TimelineFragment, TimelineViewModel)"
            constraints = listOf(Constraint.NONEMPTY)
        }

        val layoutName = stringParameter {
            name = "Название layout"
            default = ""
            help = "Название лаяута"
            constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
            suggest = { fragmentToLayout(entityName.value.lowercase()) }
        }

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

        val generateRoutingModel: BooleanParameter = booleanParameter {
            name = "Добавить состояние роутинга"
            default = true
            help = "Добавляет базовую модель роутинга в Fragment и ViewModel"
        }

        val generateUiStateModel: BooleanParameter = booleanParameter {
            name = "Добавить состояние UI"
            default = true
            help = "Добавляет базовую модель роутинга в Fragment и ViewModel"
        }

        widgets(
            TextFieldWidget(entityName),
            TextFieldWidget(layoutName),
            PackageNameWidget(packageNameParam),
            CheckBoxWidget(generateRoutingModel),
            CheckBoxWidget(generateUiStateModel),
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
                        packageName = packageName,
                        layoutName = layoutName.value,
                    ),
                    isGenerateUiStateModel = generateUiStateModel.value,
                    isGenerateRoutingStateModel = generateRoutingModel.value,
                )
            )
        }
    }

private fun generateRealPackageName(packageName: String, entityName: String): String {
    //это сделано для того, чтобы если разработчик создает сущность выбрав корневой пакет,
    // то внутри пакета создастся правильная структура пакетов
    val entityPackageSuffix = Regex("[^A-Za-z0-9 ]")
        .replace(entityName, "")
        .lowercase()
        .let {
            if (it.isNotBlank()) {
                ".$it"
            } else {
                it
            }
        }

    return if (packageName == getRootPackageName(packageName = packageName)) {
        getPresentationPackageName(targetPackageName = packageName) + entityPackageSuffix
    } else if (packageName.endsWith(presentationPackageEndWith)) {
        "$packageName$entityPackageSuffix"
    } else {
        packageName
    }
}

private fun getLayoutName(packageName: String, layoutName: String): String {
    return if (packageName.startsWith("ru.pochtabank.")) {
        packageName
            .removePrefix("ru.pochtabank.")
            .substringBefore(".")
            .let { moduleName ->
                //в начала названия лаяута ставим название модуля
                "${moduleName.lowercase()}_${layoutName}"
            }
    } else {
        layoutName
    }
}
