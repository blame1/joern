package tests.cfgCreation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Vector;

import org.junit.Test;

import cfg.BasicBlock;
import cfg.CFG;
import cfg.Edges;

public class OtherTests extends CFGCreatorTest
{

	@Test
	public void testSingleCallBlockNumber()
	{
		String input = "foo();";
		CFG cfg = getCFGForCode(input);
		assertTrue(cfg.getBasicBlocks().size() == 2);
	}
	
	
	@Test
	public void testWhileNumberOfBlocks()
	{
		String input = "while(foo){ bar(); }";
		CFG cfg = getCFGForCode(input);
		assertTrue(cfg.getBasicBlocks().size() == 4);
	}
	
	@Test
	public void testDoNumberOfBlocks()
	{
		String input = "do{ bar(); }while(foo);";
		CFG cfg = getCFGForCode(input);
		System.out.println(cfg.getBasicBlocks().size());
		assertTrue(cfg.getBasicBlocks().size() == 4);
	}
	
	@Test
	public void testForNumberOfBlocks()
	{
		String input = "for(i = 0; i < 10; i ++){ foo(); }";
		CFG cfg = getCFGForCode(input);
		assertTrue(cfg.getBasicBlocks().size() == 5);
	}
	
	@Test
	public void testEmptyFor()
	{
		String input = "for(;;){}";
		CFG cfg = getCFGForCode(input);
		assertTrue(cfg.getBasicBlocks().get(1).getASTNode() == null);
	}
	
	
	@Test
	public void testLabel()
	{
		String input = "foo: foo();";
		CFG cfg = getCFGForCode(input);
		HashMap<String, BasicBlock> labels = cfg.getLabels();
		System.out.println(labels.size());
		assertTrue(labels.size() == 1);
	}
	
	@Test
	public void testSwitchNumberOfEdges()
	{
		String input = "switch(foo){ case 1: case2: case 3: }";
		CFG cfg = getCFGForCode(input);
		Edges edges = cfg.getEdges();
		System.out.println(edges.size());
		assertTrue(edges.size() == 9);
	}
	
	@Test
	public void testTwoInstructions()
	{
		String input = "x = 10; y = 20;";
		CFG cfg = getCFGForCode(input);
		assertTrue(cfg.getBasicBlocks().size() == 3);
	}
	
	@Test
	public void testLinkBetweenBlocks()
	{
		String input = "x = 10; y = 20;";
		CFG cfg = getCFGForCode(input);
		Edges edges = cfg.getEdges();
		assertTrue(cfg.getEdges().size() == 2);
	}
	
	@Test
	public void testReturnExitBlock()
	{
		String input = "if(!x) return 1; y = x; return 0;";
		CFG cfg = getCFGForCode(input);
		
		BasicBlock exitBlock = cfg.getBasicBlocks().lastElement();		
		assertTrue(exitBlock.getEscapedCodeStr().equals(""));
	}
	
	@Test
	public void testReturnOneExitBlock()
	{
		String input = "if(!x) return 1; y = x;";
		CFG cfg = getCFGForCode(input);
		
		BasicBlock yAssignX = cfg.getBasicBlocks().get(3);
		BasicBlock exitBlock = cfg.getBasicBlocks().lastElement();
		
		assertTrue(cfg.getEdges().getEdgesFrom(yAssignX).contains(exitBlock));
	}
	
	@Test
	public void testGoto()
	{
		String input = "x = 0; foo: x++; if(x < 10) goto foo;";
		CFG cfg = getCFGForCode(input);
		
		Vector<BasicBlock> basicBlocks = cfg.getBasicBlocks();
		
		BasicBlock gotoStmt = basicBlocks.get(4);
		BasicBlock exitBlock = cfg.getLastBlock();
		
		assertFalse(cfg.getEdges().getEdgesFrom(gotoStmt).contains(exitBlock));
	}

}
