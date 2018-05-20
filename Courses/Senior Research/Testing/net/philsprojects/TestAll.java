package net.philsprojects;

import net.philsprojects.data.TestBits;
import net.philsprojects.data.TestDataArray;
import net.philsprojects.data.TestDataSet;
import net.philsprojects.data.TestStores;
import net.philsprojects.data.store.TestFileStore;
import net.philsprojects.data.store.TestMappedStore;
import net.philsprojects.data.store.TestMemoryStore;
import net.philsprojects.data.var.TestBooleanVar;
import net.philsprojects.data.var.TestByteVar;
import net.philsprojects.data.var.TestDoubleVar;
import net.philsprojects.data.var.TestFloatVar;
import net.philsprojects.data.var.TestIntVar;
import net.philsprojects.data.var.TestLongVar;
import net.philsprojects.data.var.TestShortVar;
import net.philsprojects.data.var.TestStringVar;
import net.philsprojects.data.var.TestUByteVar;
import net.philsprojects.data.var.TestUIntVar;
import net.philsprojects.data.var.TestUShortVar;
import net.philsprojects.io.TestBufferStream;
import net.philsprojects.io.TestBuffers;
import net.philsprojects.io.buffer.TestBufferFactoryBinary;
import net.philsprojects.io.buffer.TestBufferFactoryDirect;
import net.philsprojects.io.buffer.TestBufferFactoryFixed;
import net.philsprojects.io.buffer.TestBufferFactoryHeap;
import net.philsprojects.io.buffer.TestBufferFactoryMap;
import net.philsprojects.io.bytes.TestByteWriterReader;
import net.philsprojects.live.TestStrategy;
import net.philsprojects.live.prop.TestPropertyBoolean;
import net.philsprojects.live.prop.TestPropertyByte;
import net.philsprojects.live.prop.TestPropertyDouble;
import net.philsprojects.live.prop.TestPropertyFloat;
import net.philsprojects.live.prop.TestPropertyInt;
import net.philsprojects.live.prop.TestPropertyLong;
import net.philsprojects.live.prop.TestPropertyShort;
import net.philsprojects.live.prop.TestPropertyString;
import net.philsprojects.net.nio.TestNioSelector;
import net.philsprojects.net.nio.TestNioWorker;
import net.philsprojects.net.nio.tcp.TestTcpClient;
import net.philsprojects.net.nio.tcp.TestTcpPipeline;
import net.philsprojects.net.nio.tcp.TestTcpServer;
import net.philsprojects.service.TestService;
import net.philsprojects.stat.TestStatArchive;
import net.philsprojects.stat.TestStatDatabase;
import net.philsprojects.stat.TestStatGroup;
import net.philsprojects.stat.TestStatPoint;
import net.philsprojects.task.TestTask;
import net.philsprojects.task.TestTaskList;
import net.philsprojects.task.TestTaskSet;
import net.philsprojects.util.TestAtomicStack;
import net.philsprojects.util.TestBlockableQueue;
import net.philsprojects.util.TestNestedSync;
import net.philsprojects.util.TestLockRef;
import net.philsprojects.util.TestNonNullRef;
import net.philsprojects.util.TestNotifier;
import net.philsprojects.util.TestPerformance;
import net.philsprojects.util.TestSignal;
import net.philsprojects.util.TestState;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
{
// net.philsprojects.data
	TestBits.class,
	TestDataArray.class,
	TestDataSet.class,
	TestStores.class,
	
// net.philsprojects.data.var
	TestBooleanVar.class,
	TestByteVar.class,
	TestDoubleVar.class,
	TestFloatVar.class,
	TestIntVar.class,
	TestLongVar.class,
	TestShortVar.class,
	TestStringVar.class,
	TestUByteVar.class,
	TestUIntVar.class,
	TestUShortVar.class,
	
// net.philsprojects.data.store
	TestFileStore.class,
	TestMappedStore.class,
	TestMemoryStore.class,
	
// net.philsprojects.io
	TestBuffers.class,
	TestBufferStream.class,
	
// net.philsprojects.io.buffer	
	TestBufferFactoryBinary.class,
	TestBufferFactoryDirect.class,
	TestBufferFactoryFixed.class,
	TestBufferFactoryHeap.class,
	TestBufferFactoryMap.class,
	
// net.philsprojects.io.bytes
	TestByteWriterReader.class,

// net.philsprojects.live
	TestStrategy.class,
	
// net.philsprojects.live.prop
	TestPropertyBoolean.class,
	TestPropertyByte.class,
	TestPropertyDouble.class,
	TestPropertyFloat.class,
	TestPropertyInt.class,
	TestPropertyLong.class,
	TestPropertyShort.class,
	TestPropertyString.class,

// net.philsprojects.msg
	
// net.philsprojects.net.nio
	TestNioSelector.class,
	TestNioWorker.class,
	
// net.philsprojects.net.nio.tcp
	TestTcpClient.class,
	TestTcpPipeline.class,
	TestTcpServer.class,
	
// net.philsprojects.service
	TestService.class,
//	TestServicePool.class,
	
// net.philsprojects.stat
	TestStatArchive.class,
	TestStatPoint.class,
	TestStatGroup.class,
	TestStatDatabase.class,
	
// net.philsprojects.task	
	TestTask.class,
	TestTaskList.class,
	TestTaskSet.class,
	
// net.philsprojects.util
	TestAtomicStack.class,
	TestBlockableQueue.class,
	TestLockRef.class,
	TestNestedSync.class,
	TestNonNullRef.class,
	TestNotifier.class,
	TestPerformance.class,
	TestSignal.class,
	TestState.class,
})
public class TestAll 
{
}
