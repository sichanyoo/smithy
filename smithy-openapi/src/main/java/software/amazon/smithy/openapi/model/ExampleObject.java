/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.smithy.openapi.model;

import java.util.Optional;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.utils.SmithyBuilder;
import software.amazon.smithy.utils.ToSmithyBuilder;

public final class ExampleObject extends Component implements ToSmithyBuilder<ExampleObject> {
    private final String summary;
    private final String description;
    private final ObjectNode value;
    private final String externalValue;

    private ExampleObject(Builder builder) {
        super(builder);
        this.summary = SmithyBuilder.requiredState("summary", builder.summary);
        this.description = builder.description;
        this.value = builder.value;
        this.externalValue = builder.externalValue;
    }

    public static Builder builder() {
        return new Builder();
    }

    // getters
    public String getSummary() {
        return summary;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<ObjectNode> getValue() {
        return Optional.ofNullable(value);
    }

    public Optional<String> getExternalValue() {
        return Optional.ofNullable(externalValue);
    }

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
        private ObjectNode value;
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

        public Builder value(ObjectNode value) {
            this.value = value;
            return this;
        }

        public Builder externalValue(String externalValue) {
            this.externalValue = externalValue;
            return this;
        }
    }
}
