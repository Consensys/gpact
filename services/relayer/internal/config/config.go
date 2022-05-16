package config

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
	"github.com/consensys/gpact/services/relayer/internal/logging"
	"github.com/spf13/viper"
)

// Config defines the server configuration.
type Config struct {
	// Logging related settings
	LogServiceName string `mapstructure:"LOG_SERVICE_NAME"` // Log service name
	LogLevel       string `mapstructure:"LOG_LEVEL"`        // Log Level: NONE, ERROR, WARN, INFO, TRACE
	LogTarget      string `mapstructure:"LOG_TARGET"`       // Log Level: STDOUT
	LogDir         string `mapstructure:"LOG_DIR"`          // Log Dir: /var/.relayer/log
	LogFile        string `mapstructure:"LOG_FILE"`         // Log File: relayer.log
	LogMaxBackups  int    `mapstructure:"LOG_MAX_BACKUPS"`  // Log max backups: 3
	LogMaxAge      int    `mapstructure:"LOG_MAX_AGE"`      // Log max age (days): 28
	LogMaxSize     int    `mapstructure:"LOG_MAX_SIZE"`     // Log max size (MB): 500
	LogCompress    bool   `mapstructure:"LOG_COMPRESS"`     // Log compress: false
	LogTimeFormat  string `mapstructure:"LOG_TIME_FORMAT"`  // Log time format: RFC3339

	// MessageQueue related
	InboundMQAddr  string `mapstructure:"INBOUND_MQ_ADDR"`  // Inbound message queue addr
	InboundChName  string `mapstructure:"INBOUND_CH_NAME"`  // Inbound message queue name
	OutboundMQAddr string `mapstructure:"OUTBOUND_MQ_ADDR"` // Outbound message queue addr
	OutboundChName string `mapstructure:"OUTBOUND_CH_NAME"` // Outbound message queue name

	// Admin server related
	APIPort int `mapstructure:"API_PORT"` // API Port: 9425

	// Signer datastore location
	SignerDSPath string `mapstructure:"SIGNER_DS_PATH"` // Signer DS Path: /var/.relayer/ds0

	// Transactor datastore location
	TransactorDSPath string `mapstructure:"TRANSACTOR_DS_PATH"` // Transactor DS Path: /var/.relayer/ds1

	// Verifier datastore location
	VerifierDSPath string `mapstructure:"VERIFIER_DS_PATH"` // Verifier DS Path: /var/.relayer/ds2

	// Observer datastore locaion
	ObserverDSPath string `mapstructure:"OBSERVER_DS_PATH"` // Observer DS Path: /var/.relayer/ds3

	// Relayer routes datastore location
	RelayerRoutesDSPath string `mapstructure:"RELAYER_ROUTES_DS_PATH"` // Relayer Routes DS Path: /var/.relayer/ds4

}

// NewConfig reads environmental variables and creates a new configuration.
func NewConfig() Config {
	conf := viper.New()
	conf.AutomaticEnv()
	logging.Init(conf)

	return Config{
		LogServiceName: conf.GetString("LOG_SERVICE_NAME"),
		LogLevel:       conf.GetString("LOG_LEVEL"),
		LogTarget:      conf.GetString("LOG_TARGET"),
		LogDir:         conf.GetString("LOG_DIR"),
		LogFile:        conf.GetString("LOG_FILE"),
		LogMaxBackups:  conf.GetInt("LOG_MAX_BACKUPS"),
		LogMaxAge:      conf.GetInt("LOG_MAX_AGE"),
		LogMaxSize:     conf.GetInt("LOG_MAX_SIZE"),
		LogCompress:    conf.GetBool("LOG_COMPRESS"),
		LogTimeFormat:  conf.GetString("LOG_TIME_FORMAT"),

		InboundMQAddr:  conf.GetString("INBOUND_MQ_ADDR"),
		InboundChName:  conf.GetString("INBOUND_CH_NAME"),
		OutboundMQAddr: conf.GetString("OUTBOUND_MQ_ADDR"),
		OutboundChName: conf.GetString("OUTBOUND_CH_NAME"),

		APIPort:             conf.GetInt("API_PORT"),
		SignerDSPath:        conf.GetString("SIGNER_DS_PATH"),
		TransactorDSPath:    conf.GetString("TRANSACTOR_DS_PATH"),
		VerifierDSPath:      conf.GetString("VERIFIER_DS_PATH"),
		ObserverDSPath:      conf.GetString("OBSERVER_DS_PATH"),
		RelayerRoutesDSPath: "./relayer-routes/",
	}
}
