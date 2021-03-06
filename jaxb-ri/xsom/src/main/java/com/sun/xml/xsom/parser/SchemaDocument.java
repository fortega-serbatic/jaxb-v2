/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.xml.xsom.parser;

import com.sun.xml.xsom.XSSchema;

import java.util.Set;

/**
 * Represents a parsed XML schema document.
 *
 * <p>
 * Unlike schema components defined in {@code XS****} interfaces,
 * which are inherently de-coupled from where it was loaded from,
 * {@link SchemaDocument} represents a single XML infoset that
 * is a schema document.
 *
 * <p>
 * This concept is often useful in tracking down the reference
 * relationship among schema documents.
 *
 * @see XSOMParser#getDocuments()
 * @author Kohsuke Kawaguchi
 */
public interface SchemaDocument {
    /**
     * Gets the system ID of the schema document.
     *
     * @return
     *      null if {@link XSOMParser} was not given the system Id.
     */
    String getSystemId();

    /**
     * The namespace that this schema defines.
     *
     * <p>
     * More precisely, this method simply returns the {@code targetNamespace} attribute
     * of the schema document. When schemas are referenced in certain ways
     * (AKA chameleon schema), schema components in this schema document
     * may end up defining components in other namespaces.
     *
     * @return
     *      can be "" but never null.
     */
    String getTargetNamespace();

    /**
     * Gets {@link XSSchema} component that contains all the schema
     * components defined in this namespace.
     *
     * <p>
     * The returned {@link XSSchema} contains not just components
     * defined in this {@link SchemaDocument} but all the other components
     * defined in all the schemas that collectively define this namespace.
     *
     * @return
     *      never null.
     */
    XSSchema getSchema();

    /**
     * Set of {@link SchemaDocument}s that are included/imported from this document.
     *
     * @return
     *      can be empty but never null. read-only.
     */
    Set<SchemaDocument> getReferencedDocuments();

    /**
     * Gets the {@link SchemaDocument}s that are included from this document.
     *
     * @return
     *      can be empty but never null. read-only.
     *      this set is always a subset of {@link #getReferencedDocuments()}.
     */
    Set<SchemaDocument> getIncludedDocuments();

    /**
     * Gets the {@link SchemaDocument}s that are imported from this document.
     *
     * @param targetNamespace
     *      The namespace URI of the import that you want to
     *      get {@link SchemaDocument}s for.
     * @return
     *      can be empty but never null. read-only.
     *      this set is always a subset of {@link #getReferencedDocuments()}.
     */
    Set<SchemaDocument> getImportedDocuments(String targetNamespace);

    /**
     * Returns true if this document includes the given document.
     *
     * <p>
     * Note that this method returns false if this document
     * imports the given document.
     */
    boolean includes(SchemaDocument doc);

    /**
     * Returns true if this document imports the given document.
     *
     * <p>
     * Note that this method returns false if this document
     * includes the given document.
     */
    boolean imports(SchemaDocument doc);

    /**
     * Set of {@link SchemaDocument}s that include/import this document.
     *
     * <p>
     * This works as the opposite of {@link #getReferencedDocuments()}.
     *
     * @return
     *      can be empty but never null. read-only.
     */
    Set<SchemaDocument> getReferers();
}
