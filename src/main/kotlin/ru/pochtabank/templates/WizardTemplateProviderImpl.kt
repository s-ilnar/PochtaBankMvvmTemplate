package ru.pochtabank.templates

import com.android.tools.idea.wizard.template.Template
import com.android.tools.idea.wizard.template.WizardTemplateProvider
import ru.pochtabank.templates.activityTest.emptyActivityTemplate
import ru.pochtabank.templates.newModuleModule.newModuleSetupTemplate
import ru.pochtabank.templates.viewModelFragment.mvvmFragmentSetupTemplate

class WizardTemplateProviderImpl : WizardTemplateProvider() {

    override fun getTemplates(): List<Template> = listOf(
        mvvmFragmentSetupTemplate,
        newModuleSetupTemplate,
      //  emptyActivityTemplate
    )
}