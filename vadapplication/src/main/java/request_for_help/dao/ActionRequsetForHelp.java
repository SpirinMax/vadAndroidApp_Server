package request_for_help.dao;

import request_for_help.service.RequestForHelp;
import user.profile.User;

public interface ActionRequsetForHelp {
	void createRequestForHelp (RequestForHelp request, User author);
	
}
