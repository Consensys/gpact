/*
Package logging - common package, used for logging purposes. Is a wrapper around 3-rd party logging framework.
*/
package logging

/*
 * Copyright 2022 ConsenSys Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

import (
	"fmt"
	"testing"

	"github.com/rs/zerolog"
	"github.com/spf13/viper"
	"github.com/stretchr/testify/assert"
)

func ExampleInit() {
	conf := viper.New()
	l := Init(conf)
	fmt.Printf("%T\n", l)
	// Output: zerolog.Logger
}

func Example_setTimeFormat_RFC() {
	conf := viper.New()
	conf.Set("LOG_TIME_FORMAT", "RFC3339")
	setTimeFormat(conf)
	fmt.Println(zerolog.TimeFieldFormat)
	// Output: 2006-01-02T15:04:05Z07:00
}

func Example_setTimeFormat_Unix() {
	conf := viper.New()
	conf.Set("LOG_TIME_FORMAT", "Unix")
	setTimeFormat(conf)
	fmt.Println(zerolog.TimeFieldFormat)
	// Output:
}

func ExampleInfo() {
	Trace("trace")
	Debug("debug")
	Info("info")
	Warn("warn")
	Error("error")
	// Output:
}

func Example_setLogLevel(t *testing.T) {
	assert.Equal(t, zerolog.GlobalLevel(), "info")

	conf := viper.New()
	conf.Set("LOG_LEVEL", "AA")
	setLogLevel(conf)
	assert.Equal(t, zerolog.GlobalLevel(), "info")

	conf.Set("LOG_LEVEL", "debug")
	setLogLevel(conf)
	assert.Equal(t, zerolog.GlobalLevel(), "debug")
}

func ExampleSetLogLevel(t *testing.T) {
	err := SetLogLevel("AA")
	assert.NotEmpty(t, err)
	SetLogLevel("debug")
	assert.Empty(t, err)
	assert.Equal(t, zerolog.GlobalLevel(), "debug")
}

func ExamplePanic() {
	SetLogLevel("error")
	defer func() {
		if err := recover(); err != nil {
			fmt.Println(err)
		}
	}()
	Panic("test Panic message")
	// Output: test Panic message
}

func Example_getLogTarget() {
	conf := viper.New()
	conf.Set("LOG_TARGET", "FILE")
	conf.Set("LOG_DIR", ".")
	conf.Set("LOG_FILE", "test.out")
	v := getLogTarget(conf)
	conf.Set("LOG_TARGET", "stdout")
	v = getLogTarget(conf)
	fmt.Printf("%T\n", v)
	// Output: *os.File
}

func Example_newLogTargetFile() {
	conf := viper.New()
	conf.Set("LOG_DIR", "")
	newLogTargetFile(conf)

	conf.Set("LOG_TARGET", "FILE")
	conf.Set("LOG_DIR", ".")
	conf.Set("LOG_FILE", "test.out")
	newLogTargetFile(conf)

	conf.Set("LOG_TARGET", "stdout")
	newLogTargetFile(conf)
	// Output:
}
func ExampleInitWithoutConfig() {
	l := InitWithoutConfig("logLevel", "init1.out", "logServiceName", "timeFormat")
	fmt.Printf("%T\n", l)
	// Output: zerolog.Logger
}
