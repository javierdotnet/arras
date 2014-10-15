package org.github.fscheffer.arras.components;

import javax.inject.Inject;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.runtime.RenderCommand;
import org.github.fscheffer.arras.ArrasUtils;
import org.github.fscheffer.arras.TabGroupContext;

/**
 *
 * @tapestrydoc
 * @see TabGroup
 * @see TabDropdown
 */
public class Tab {

    /**
     * Optional parameter. When set to true the tab will be re-rendered when the user clicks on it.
     */
    @Parameter
    private boolean            ajax;

    /**
     * A renderable (usually a {@link Block}) that can render the label for a
     * tab. This will be invoked after the "current" property of {@link TabGroupContext} has been
     * updated.
     */
    @Parameter(value = "block:defaultLabel")
    private RenderCommand      label;

    @Environmental
    private TabGroupContext    context;

    @Inject
    private Block              content;

    @Inject
    private ComponentResources resources;

    @BeginRender
    RenderCommand begin(MarkupWriter writer) {

        this.context.setCurrent(getId());
        this.context.addContent((RenderCommand) this.content);

        Element li = writer.element("li");

        if (this.context.isActive(getId())) {
            li.attribute("class", "active");
        }

        if (this.ajax) {
            Link link = this.resources.createEventLink("updateTab");
            li.attribute("data-update-tab", link.toString());
        }

        writer.element("a", "href", "#" + getId(), "role", "tab", "data-toggle", "tab");

        return this.label;
    }

    @AfterRender
    void after(MarkupWriter writer) {
        writer.end();
        writer.end();
    }

    public String getId() {
        return this.resources.getId();
    }

    public boolean isRenderContent() {
        return !this.ajax || this.context.isActive(getId());
    }

    public String getName() {
        return ArrasUtils.getPresentableComponentName(this.context.getMessages(), this.resources);
    }

    public String getPaneClasses() {

        boolean active = this.context.isActive(getId());

        StringBuilder builder = new StringBuilder();
        builder.append("tab-pane");

        if (active) {
            builder.append(" active");
        }

        if (this.context.isFade()) {
            builder.append(" fade");

            if (active) {
                builder.append(" in");
            }
        }

        return builder.toString();
    }

    @OnEvent(value = "updateTab")
    Block onUpdateTab() {
        return this.resources.getBody();
    }
}
