/*
 *
 * Copyright 2018 Observational Health Data Sciences and Informatics
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

package com.odysseusinc.athena.api.v1.controller.converter;

import static com.odysseusinc.athena.api.v1.controller.converter.ConceptSearchDTOToSolrQuery.getFacetLabel;
import static java.util.stream.Collectors.toList;

import com.google.common.collect.ComparisonChain;
import com.odysseusinc.athena.api.v1.controller.dto.ConceptDTO;
import com.odysseusinc.athena.api.v1.controller.dto.ConceptSearchResultDTO;
import com.odysseusinc.athena.service.impl.solr.SearchResult;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class ConceptSearchResultToDTO {

    public ConceptSearchResultDTO convert(SearchResult<SolrDocument> source) {

        return new ConceptSearchResultDTO(
                buildContent(source),
                buildFacets(source),
                buildPageRequest(source),
                getTotal(source)
        );
    }

    private List<ConceptDTO> buildContent(SearchResult<SolrDocument> searchResult) {

        return searchResult.getEntityList().stream()
                .map(SolrDocumentToConceptDTO::convert)
                .collect(toList());
    }

    private Map<String, Map<String, Long>> buildFacets(SearchResult<SolrDocument> source) {

        NamedList response = source.getSolrResponse().getResponse();
        Map facetsMap = (Map) response.asMap(10).get("facets");

        Map<String, Map<String, Long>> facets = new HashMap<>();
        List<FacetField> facetFields = source.getSolrResponse().getFacetFields();
        if (facetFields != null) {

            for (FacetField facetField : facetFields) {
                HashMap<String, Long> facetOptionList = getFacetOptionList(facetsMap, facetField.getName());
                facets.put(facetField.getName(), convertToSortMap(facetOptionList));
            }
        }
        return facets;
    }

    @SuppressWarnings(value = "unchecked")
    private HashMap<String, Long> getFacetOptionList(Map facets, String facetName) {

        Map facetLabel = (Map) facets.get(getFacetLabel(facetName));
        List<SimpleOrderedMap> buckets = (List<SimpleOrderedMap>) facetLabel.get("buckets");
        HashMap<String, Long> facetOptionList = new HashMap<>();

        buckets.forEach(each -> {
            Map entry = each.asMap(2);
            final String val = (String) entry.get("val");
            final Long count = ((Integer) entry.get("count")).longValue();
            facetOptionList.put(val, count);
        });

        return facetOptionList;
}

    private PageRequest buildPageRequest(SearchResult source) {

        Integer itemsOnPage = source.getSolrQuery().getRows();
        Integer pageNum = 1;
        if (source.getSolrQuery().getStart() > 0) {
            pageNum += (source.getSolrQuery().getStart() / source.getSolrQuery().getRows());
        }
        return new PageRequest(pageNum, itemsOnPage);
    }

    private long getTotal(SearchResult source) {

        return source.getSolrResponse().getResults().getNumFound();
    }


    private Map<String, Long> convertToSortMap(Map<String, Long> map) {

        List<Map.Entry<String, Long>> entries = new LinkedList<>(map.entrySet());
        entries.sort((o1, o2) -> ComparisonChain.start()
                .compareTrueFirst(o2.getValue() == 0, o1.getValue() == 0)
                .compare(o1.getKey(), o2.getKey()).result());

        Map<String, Long> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Long> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}