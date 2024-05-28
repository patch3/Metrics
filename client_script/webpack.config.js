const path = require('path');

module.exports = {
    mode: 'production',
//    mode: 'development',
    entry: './src/MetricsHook.ts',
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
        ],
    },
    resolve: {
        extensions: ['.tsx', '.ts', '.js'],
    },
    output: {
        filename: 'metricshook.bundle.js',
        path: path.resolve(__dirname, 'dist'),
    },
};