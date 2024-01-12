package ru.pochtabank.templates

import com.android.tools.idea.wizard.template.Constraint
import com.android.tools.idea.wizard.template.StringParameter
import com.android.tools.idea.wizard.template.stringParameter
import ru.pochtabank.templates.core.utils.getPresentationPackageName
import ru.pochtabank.templates.core.utils.getRootPackageName

val defaultPackageNameParameter
    get() = stringParameter {
        name = "Имя пакета"
        visible = { !isNewModule }
        default = "ru.pochtabank.myapp"
        constraints = listOf(Constraint.PACKAGE)
        suggest = { packageName }
    }

val mvvmPackageNameParameter
    get() = stringParameter {
        name = "Имя пакета"
        visible = { !isNewModule }
        default = "ru.pochtabank.myapp"
        constraints = listOf(Constraint.PACKAGE)
        suggest = {
            if (packageName == getRootPackageName(packageName)) {
                //тоже пока костыль, может получится как-то по другому сделать
                getPresentationPackageName(targetPackageName = packageName)
            } else {
                packageName
            }
        }
    }

val invisibleSourceProviderNameParameter: StringParameter
    get() = stringParameter {
        name = "Source Provider Name"
        constraints = listOf()
        default = ""
        visible = { false }
        suggest = { sourceProviderName }
    }