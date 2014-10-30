// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.github.fscheffer.arras.cms.demo.components;

import javax.inject.Inject;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.github.fscheffer.arras.cms.demo.services.UserManager;

@Import(stylesheet = "arras/sample.css")
public class Layout {

    @Persist
    @Property
    private String      username, password;

    @Inject
    private UserManager userManager;

    @OnEvent(value = EventConstants.SUCCESS, component = "loginForm")
    void onLogin() {
        this.userManager.login();
    }

    @OnEvent(value = "logout")
    void onLogout() {
        this.userManager.logout();
    }

    public boolean isLoggedIn() {
        return this.userManager.isLoggedIn();
    }
}
