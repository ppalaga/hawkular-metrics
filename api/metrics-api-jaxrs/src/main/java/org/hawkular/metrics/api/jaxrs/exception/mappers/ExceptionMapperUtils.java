/*
 * Copyright 2014-2015 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.metrics.api.jaxrs.exception.mappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hawkular.metrics.model.ApiError;
import org.jboss.logging.Logger;

import com.google.common.base.Throwables;

/**
 * @author Jeeva Kandasamy
 */
public class ExceptionMapperUtils {
    private static final Logger log = Logger.getLogger(ExceptionMapperUtils.class);

    public static Response buildResponse(Throwable exception, Status status) {
        Response response = Response.status(status)
                .entity(new ApiError(Throwables.getRootCause(exception).getMessage()))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
        if (log.isTraceEnabled()) {
            log.trace("Turning " + exception.getClass().getCanonicalName() + " into a " + response.getStatus() + " " +
                    "response", exception);
        }
        return response;
    }

    private ExceptionMapperUtils() {
        // Utility class
    }
}
