package com.airtribe.coderank.repository;

import com.airtribe.coderank.entity.CodeSnippet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeSnippetRepository extends CrudRepository<CodeSnippet, Long> {

    @Query("SELECT snippetId FROM CodeSnippet WHERE executionId = ?1")
    Long findIdByExecutionId(String executionId);

    @Query("SELECT c FROM CodeSnippet c WHERE c.executionId = ?1")
    CodeSnippet findCodeSnippetByExecutionId(String executionId);

}
