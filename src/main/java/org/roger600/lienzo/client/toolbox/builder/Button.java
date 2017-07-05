/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.roger600.lienzo.client.toolbox.builder;

import org.roger600.lienzo.client.toolbox.event.ToolboxButtonEventHandler;

public interface Button {

    Button setPadding(final int padding);

    Button setIconSize(final int iconSize);

    Button setClickHandler(final ToolboxButtonEventHandler handler);

    Button setMouseDownHandler(final ToolboxButtonEventHandler handler);

    Button setMouseEnterHandler(final ToolboxButtonEventHandler handler);

    Button setMouseExitHandler(final ToolboxButtonEventHandler handler);

    ButtonsOrRegister end();
}
