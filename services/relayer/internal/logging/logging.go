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
	"errors"
	"io"
	"os"
	"path"
	"time"

	"github.com/rs/zerolog"
	"github.com/rs/zerolog/log"
	"github.com/spf13/viper"
	"gopkg.in/natefinch/lumberjack.v2"
)

// Init initialises the logger with a Viper object.
func Init(conf *viper.Viper) zerolog.Logger {
	setLogLevel(conf)
	setTimeFormat(conf)
	writer := getLogTarget(conf)
	service := getLogServiceName(conf)
	logger := zerolog.New(writer).With().Timestamp().Str("service", service).Logger()
	log.Logger = logger.With().Caller().Logger()
	return logger
}

// InitWithoutConfig initialises the logger without a Viper object.
func InitWithoutConfig(logLevel string, logTarget string, logServiceName string, timeFormat string) zerolog.Logger {
	conf := viper.New()
	conf.Set("LOG_LEVEL", logLevel)
	conf.Set("LOG_TARGET", logTarget)
	conf.Set("LOG_SERVICE_NAME", logServiceName)
	conf.Set("LOG_TIME_FORMAT", timeFormat)
	return Init(conf)
}

// setLogLevel sets the logging level. Set to "info" level if the log level is invalid.
// As this function is called from the init function, it must set a log level.
func setLogLevel(conf *viper.Viper) {
	logLevel := conf.GetString("LOG_LEVEL")
	level, err := zerolog.ParseLevel(logLevel)
	if err != nil {
		log.Error().Err(err).Msg("can't parse log level")
		level = zerolog.InfoLevel
		log.Warn().Msgf("using level '%v' as default", level)
	}
	zerolog.SetGlobalLevel(level)
}

// SetLogLevel sets the logging level.
func SetLogLevel(logLevel string) error {
	level, err := zerolog.ParseLevel(logLevel)
	if err != nil {
		log.Error().Err(err).Str("level", logLevel).Msg("can't parse log level")
		return errors.New("ignoring invalid log level")
	} else {
		zerolog.SetGlobalLevel(level)
		return nil
	}
}

// setTimeFormat sets the time format.
func setTimeFormat(conf *viper.Viper) {
	format := conf.GetString("LOG_TIME_FORMAT")
	switch format {
	case "RFC3339":
		zerolog.TimeFieldFormat = time.RFC3339
	case "Unix":
		zerolog.TimeFieldFormat = zerolog.TimeFormatUnix
	default: //Do nothing, use default
	}
}

// getLogServiceName gets the logging service name.
func getLogServiceName(conf *viper.Viper) string {
	logLogger := conf.GetString("LOG_SERVICE_NAME")
	return logLogger
}

// getLogTarget gets the logging target.
func getLogTarget(conf *viper.Viper) io.Writer {
	logTarget := conf.GetString("LOG_TARGET")
	switch logTarget {
	case "FILE":
		return newLogTargetFile(conf)
	default:
		return os.Stdout
	}
}

// newLogTargetFile creates a logging target file.
func newLogTargetFile(conf *viper.Viper) io.Writer {
	// TODO: Log file not created. We need to fix it
	logDir := conf.GetString("LOG_DIR")
	if err := os.MkdirAll(logDir, 0744); err != nil {
		log.Error().Err(err).Str("path", logDir).Msg("can't create log directory")
		return nil
	}
	return &lumberjack.Logger{
		Filename:   path.Join(logDir, conf.GetString("LOG_FILE")),
		MaxBackups: conf.GetInt("LOG_MAX_BACKUPS"),
		MaxAge:     conf.GetInt("LOG_MAX_AGE"),
		MaxSize:    conf.GetInt("LOG_MAX_SIZE"),
		Compress:   conf.GetBool("LOG_COMPRESS"),
	}
}

// Trace is wrapper over logger's trace.
func Trace(msg string, args ...interface{}) {
	log.Trace().Msgf(msg, args...)
}

// Debug is wrapper over logger's debug.
func Debug(msg string, args ...interface{}) {
	log.Debug().Msgf(msg, args...)
}

// Info is wrapper over logger's info.
func Info(msg string, args ...interface{}) {
	log.Info().Msgf(msg, args...)
}

// Warn is wrapper over logger's warn.
func Warn(msg string, args ...interface{}) {
	log.Warn().Msgf(msg, args...)
}

// Error is wrapper over logger's error.
func Error(msg string, args ...interface{}) {
	log.Error().Msgf(msg, args...)
}

// Fatal is wrapper over logger's fatal.
func Fatal(msg string, args ...interface{}) {
	log.Fatal().Msgf(msg, args...)
}

// Panic is wrapper over logger's panic.
func Panic(msg string, args ...interface{}) {
	log.Panic().Msgf(msg, args...)
}
