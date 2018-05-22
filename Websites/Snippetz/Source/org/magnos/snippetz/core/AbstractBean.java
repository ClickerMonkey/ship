package org.magnos.snippetz.core;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;


public class AbstractBean
{
	
	public void addMessage( String clientId, String summary, String detail, Severity severity )
	{
		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setDetail( detail );
		facesMessage.setSummary( summary );
		facesMessage.setSeverity( severity );
		
		FacesContext.getCurrentInstance().addMessage( clientId, facesMessage );
	}

	public void addError( String clientId, String summary, String detail )
	{
		addMessage( clientId, summary, detail, FacesMessage.SEVERITY_ERROR );
	}

	public void addError( String clientId, String summary )
	{
		addMessage( clientId, summary, summary, FacesMessage.SEVERITY_ERROR );
	}

	public void addWarning( String clientId, String summary, String detail )
	{
		addMessage( clientId, summary, detail, FacesMessage.SEVERITY_WARN );
	}

	public void addWarning( String clientId, String summary )
	{
		addMessage( clientId, summary, summary, FacesMessage.SEVERITY_WARN );
	}

	public void addInfo( String clientId, String summary, String detail )
	{
		addMessage( clientId, summary, detail, FacesMessage.SEVERITY_INFO );
	}

	public void addInfo( String clientId, String summary )
	{
		addMessage( clientId, summary, summary, FacesMessage.SEVERITY_INFO );
	}
	
}
