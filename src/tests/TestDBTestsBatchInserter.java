package tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import output.neo4j.batchInserter.Neo4JBatchInserter;

public class TestDBTestsBatchInserter {

	@BeforeClass
	public static void loadTestDatabase()
	{
		Neo4JBatchInserter.setIndexDirectoryName(".testDB");
		Neo4JBatchInserter.openDatabase();
	}
	
	@AfterClass
	public static void shutdownTestDatabase()
	{
		Neo4JBatchInserter.closeDatabase();
	}
	
}
