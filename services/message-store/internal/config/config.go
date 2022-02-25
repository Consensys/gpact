package config

import "github.com/spf13/viper"

type Config struct {
	MessageDataStorePath string `mapstructure:"MESSAGE_DS_PATH"` // Message Data Store Path
	// The `host:port` to bind to and start listening on. If not provided, default to ":8080".
	ServiceAddress string `mapstructure:"SERVICE_ADDRESS"`
}

func NewConfig() Config {
	conf := viper.New()
	conf.AutomaticEnv()

	return Config{
		MessageDataStorePath: conf.GetString("MESSAGE_DS_PATH"),
		ServiceAddress:       conf.GetString("SERVICE_ADDRESS"),
	}
}
