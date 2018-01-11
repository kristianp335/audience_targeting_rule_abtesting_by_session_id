package com.liferay.content.targeting.rule;

import com.liferay.content.targeting.anonymous.users.model.AnonymousUser;
import com.liferay.content.targeting.api.model.BaseJSPRule;
import com.liferay.content.targeting.api.model.Rule;
import com.liferay.content.targeting.model.RuleInstance;
import com.liferay.content.targeting.rule.categories.SampleRuleCategory;
import com.liferay.content.targeting.rule.categories.SessionAttributesRuleCategory;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;


import org.json.simple.parser.JSONParser;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, service = Rule.class)
public class AbTestingRule extends BaseJSPRule {

	@Activate
	@Override
	public void activate() {
		super.activate();
	}

	@Deactivate
	@Override
	public void deActivate() {
		super.deActivate();
	}

	@Override
	public boolean evaluate(
			HttpServletRequest httpServletRequest, RuleInstance ruleInstance,
			AnonymousUser anonymousUser)
		throws Exception {

		// You can obtain the rule configuration from the type settings

		String abTesting = GetterUtil.getString(ruleInstance.getTypeSettings());
		
		String actualStateOfAb = "";
		
		String sessionId = httpServletRequest.getSession().getId();
		System.out.println("The session id = " + sessionId);		
		Character lastChar = sessionId.charAt(sessionId.length() -1);
		int charNumericValue = Character.getNumericValue(lastChar);	
		if (charNumericValue/2 == 0)
		{
			actualStateOfAb = "even";
		}
			else
			{
				actualStateOfAb = "odd";
			}
		
		System.out.println("The actual state of Ab is " + actualStateOfAb);	
		System.out.println("The rule state of Ab should be " + abTesting);
	    					
		return abTesting.equals(actualStateOfAb);
	}

	@Override
	public String getIcon() {
		return "icon-puzzle-piece";
	}

	@Override
	public String getRuleCategoryKey() {

		// Available category classes: BehaviourRuleCategory,
		// SessionAttributesRuleCategory, SocialRuleCategory and
		// UserAttributesRoleCategory

		return SessionAttributesRuleCategory.KEY;
	}

	@Override
	public String getSummary(RuleInstance ruleInstance, Locale locale) {
		return ruleInstance.getTypeSettings();
	}

	@Override
	public String processRule(
		PortletRequest portletRequest, PortletResponse portletResponse,
		String id, Map<String, String> values) {

		return values.get("abtesting");
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=abtesting)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected void populateContext(
		RuleInstance ruleInstance, Map<String, Object> context,
		Map<String, String> values) {

		String abtesting = "";

		if (!values.isEmpty()) {

			// Value from the request in case of an error

			abtesting = GetterUtil.getString(values.get("abtesting"));
		}
		else if (ruleInstance != null) {

			// Value from the stored configuration

			abtesting = ruleInstance.getTypeSettings();

		}

		context.put("abtesting", abtesting);
	}

	

}