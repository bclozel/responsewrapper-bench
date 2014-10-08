package org.springframework.sample;

import java.io.IOException;
import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import org.springframework.sample.support.FastByteArrayOutputStream;
import org.springframework.util.ResizableByteArrayOutputStream;

@BenchmarkMode(Mode.Throughput)
public class ResponseWrapperBenchmark {

	@State(Scope.Thread)
	public static class ResponseData {

		byte[] bytes;

		@Setup(Level.Iteration)
		public void prepare() {
			Random random = new Random();
			bytes = new byte[25 * 1024];
			random.nextBytes(bytes);
		}

	}

    @Benchmark
    public void byteArrayWrapperFixed(ResponseData data, Blackhole bh) throws IOException {
		ResizableByteArrayOutputStream baos = new ResizableByteArrayOutputStream(1024);
		baos.resize(data.bytes.length);
		baos.write(data.bytes);
		bh.consume(baos.toByteArray());
    }

	@Benchmark
	public void byteArrayWrapperUnknown(ResponseData data, Blackhole bh) throws IOException {
		ResizableByteArrayOutputStream baos = new ResizableByteArrayOutputStream(1024);
		baos.write(data.bytes);
		bh.consume(baos.toByteArray());
	}

	@Benchmark
	public void fastByteArrayWrapperFixed(ResponseData data, Blackhole bh) throws IOException {
		FastByteArrayOutputStream baos = new FastByteArrayOutputStream(1024);
		baos.resize(data.bytes.length);
		baos.write(data.bytes);
		bh.consume(baos.toByteArray());
	}

	@Benchmark
	public void fastByteArrayWrapperUnknown(ResponseData data, Blackhole bh) throws IOException {
		FastByteArrayOutputStream baos = new FastByteArrayOutputStream(1024);
		baos.write(data.bytes);
		bh.consume(baos.toByteArray());
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(ResponseWrapperBenchmark.class.getSimpleName())
				.warmupIterations(5)
				.measurementIterations(5)
				.forks(1)
				.build();

		new Runner(opt).run();
	}

}
