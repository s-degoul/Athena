/*
 *
 * Copyright 2018 Odysseus Data Services, inc.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Company: Odysseus Data Services, Inc.
 * Product Owner/Architecture: Gregory Klebanov
 * Authors: Pavel Grafkin, Vitaly Koulakov, Maria Pozhidaeva
 * Created: April 4, 2018
 *
 */

package com.odysseusinc.athena.api.v1.controller.dto.relations;

import com.google.common.base.Objects;

public class TermDTO extends ShortTermDTO {

    private String vocabularyId;
    private String conceptClassId;

    public String getVocabularyId() {

        return vocabularyId;
    }

    public void setVocabularyId(String vocabularyId) {

        this.vocabularyId = vocabularyId;
    }

    public String getConceptClassId() {

        return conceptClassId;
    }

    public void setConceptClassId(String conceptClassId) {

        this.conceptClassId = conceptClassId;
    }

    @Override
    public boolean equals(Object another) {

        if (this == another) {
            return true;
        }
        if (another == null || getClass() != another.getClass()) {
            return false;
        }
        TermDTO dto = (TermDTO) another;
        return Objects.equal(id, dto.id)
                && Objects.equal(weight, dto.weight);
    }

    @Override
    public int hashCode() {

        return Objects.hashCode(id, weight);
    }
}
