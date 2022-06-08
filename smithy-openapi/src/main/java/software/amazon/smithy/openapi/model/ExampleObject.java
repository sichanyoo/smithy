package software.amazon.smithy.openapi.model;

import java.util.Map;
import java.util.Optional;
import software.amazon.smithy.model.node.*;
import software.amazon.smithy.utils.SmithyBuilder;
import software.amazon.smithy.utils.ToSmithyBuilder;

public final class ExampleObject extends Component implements ToSmithyBuilder<ExampleObject> {
    private final String summary;
    private final String description;
    private final Map<String, Node> value;
    private final String externalValue;

    private ExampleObject(Builder builder) {
        super(builder);
        this.summary = SmithyBuilder.requiredState("summary", builder.summary);
        this.description = builder.description;
        this.value = builder.value;
        this.externalValue = builder.externalValue;
    }

    public static Builder builder() { return new Builder(); }

    // getters
    public String getSummary() { return summary; }
    public Optional<String> getDescription() { return Optional.ofNullable(description); }
    public Optional<Map<String, Node>> getValue() { return Optional.ofNullable(value); }
    public Optional<String> getExternalValue() { return Optional.ofNullable(externalValue); }

    @Override
    public Builder toBuilder() {
        return builder()
                .extensions(getExtensions())
                .summary(summary)
                .description(description)
                .value(value)
                .externalValue(externalValue);
    }

    @Override
    protected ObjectNode.Builder createNodeBuilder() {
        return Node.objectNodeBuilder()
                .withMember("summary", summary)
                .withOptionalMember("description", getDescription().map(Node::from))
                .withOptionalMember("value", getDescription().map(Node::from))
                .withOptionalMember("externalValue", getDescription().map(Node::from));

    }

    public static final class Builder extends Component.Builder<Builder, ExampleObject> {
        private String summary;
        private String description;
        private Map<String, Node> value;
        private String externalValue;

        private Builder() {}

        @Override
        public ExampleObject build() {
            return new ExampleObject(this);
        }

        // setters
        public Builder summary(String summary) {
            this.summary = summary;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder value(Map<String, Node> value) {
            this.value = value;
            return this;
        }
        public Builder externalValue(String externalValue) {
            this.externalValue = externalValue;
            return this;
        }

    }
}
