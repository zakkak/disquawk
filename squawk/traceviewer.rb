#!/usr/bin/env ruby

# Check for proper number of command line args.
expected_args=1
e_badargs=65

if ARGV.length < expected_args
  puts "Usage: #{$0} <trace> [<mapfile> [<mapfile> ...]]"
  exit e_badargs
end

trace=ARGV[0].to_s
romstart=0;

File.open(trace).each do |line|
  if line.start_with?("*TRACE*:*ROM*:")
    romstart = line.split(':')[2].to_i+4
    puts line
  elsif line.start_with?("*STACKTRACE*:")
    offset = line.split(':')[1].to_i-romstart
    flag = 0
    begin
      cmd = "grep '#{offset} :.*method'"
      if ARGV.length > 1
        i = 1
        begin
          cmd << " "+ARGV[i].to_s
          i += 1
      end until i == ARGV.length
      else
        cmd << " *.map"
      end

      l = %x[#{cmd}]
      tokens = l.split(/.* #{offset}.*method /)
      if tokens.length > 1
        puts line.gsub(/\n/, '')+"\t"+tokens[1].split[1]
        flag = 3
      else
        offset -= 768
        flag += 1
      end
    end until flag > 1
    if flag == 2
      puts line.gsub(/\n/, '')+"\tfailed"
    end
  else
    puts line
  end
end
